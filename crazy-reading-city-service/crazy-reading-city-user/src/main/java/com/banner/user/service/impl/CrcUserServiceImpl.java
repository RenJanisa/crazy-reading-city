package com.banner.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.banner.common.utils.AppJwtUtil;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.EnrollDto;
import com.banner.model.user.dtos.LoginDto;
import com.banner.model.user.dtos.SimpleUserDto;
import com.banner.model.user.dtos.UpdateDto;
import com.banner.model.user.pojos.CrcUser;
import com.banner.model.user.pojos.CrcUserInfo;
import com.banner.user.mapper.CrcUserInfoMapper;
import com.banner.user.mapper.CrcUserMapper;
import com.banner.user.service.CrcUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minio.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.banner.common.constants.RedisConstants.ENROLL_CODE_KEY;
import static com.banner.common.constants.RedisConstants.ENROLL_CODE_TIME;
import static com.banner.user.utils.UserDefaultConstants.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-20
 */
@Slf4j
@Service
public class CrcUserServiceImpl extends ServiceImpl<CrcUserMapper, CrcUser> implements CrcUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private CrcUserMapper crcUserMapper;


    @Override
    public ResponseResult login(LoginDto loginDto) {
        //获取盐和密码
        CrcUser one = this.getOne(Wrappers.<CrcUser>lambdaQuery().eq(CrcUser::getEmail, loginDto.getEmail()));
        if (one == null) {
            //用户不存在
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        String password = DigestUtil.md5Hex(loginDto.getPassword() + one.getSalt());
        if (!password.equals(one.getPassword())) {
            //用户密码错误
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        //登录成功 生成jwt
        String token = AppJwtUtil.getToken(one.getId(),one.getFlag());

        return ResponseResult.okResult(token);
    }

    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender javaMailSender;

    @Async //异步执行
    @Override
    public void sendEmail(String email,String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from + "(疯狂读书城)");
        message.setTo(email);
        message.setSubject("疯狂读书城");
        message.setText(content);
        javaMailSender.send(message);
    }

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public ResponseResult sendCode(String email) {
        //获取邮箱地址
        if (StrUtil.isBlank(email)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        //发送邮件
        String content = String.format("验证码：%s，请勿将验证码发给他人!感谢您的使用!",code);
        applicationContext.getBean(CrcUserService.class).sendEmail(email,content);

        log.info("已发送");

        //将生成的验证码保存
        stringRedisTemplate.opsForValue().set(ENROLL_CODE_KEY + email, code, ENROLL_CODE_TIME, TimeUnit.MINUTES);

        return ResponseResult.okResult(200, "成功发送");

    }


    @Override
    public ResponseResult enroll(EnrollDto enrollDto) {

        //从redis中获取验证码
//        String codeFromRedis = stringRedisTemplate.opsForValue().get(ENROLL_CODE_KEY+enrollDto.getEmail());
//        if (StrUtil.isBlank(codeFromRedis) || !codeFromRedis.equals(enrollDto.getCode())){
//            return ResponseResult.errorResult(500,"验证码错误!");
//        }
        if (this.getOne(Wrappers.<CrcUser>lambdaQuery()
                .select(CrcUser::getId).eq(CrcUser::getEmail, enrollDto.getEmail())) != null) {
            return ResponseResult.errorResult(500, "用戶已存在");
        }
        CrcUser crcUser = BeanUtil.copyProperties(enrollDto, CrcUser.class);
        String salt = RandomUtil.randomString(3);
        crcUser.setAvatar(DEFAULT_AVATAR);
        crcUser.setPassword(DigestUtil.md5Hex(enrollDto.getPassword() + salt));
        crcUser.setUserName(DEFAULT_NAME_PRE + RandomUtil.randomString(5));
        crcUser.setSalt(salt);
        crcUser.setFlag(USER);
        this.save(crcUser);

        return ResponseResult.okResult(200, "注册成功");
    }

    @Resource
    private CrcUserInfoMapper crcUserInfoMapper;

    @Override
    @Transactional
    public ResponseResult updateInfo(UpdateDto updateDto) {

        if (updateDto.getId() == null) updateDto.setId(ThreadLocalUtil.getId());
        CrcUser crcUser = BeanUtil.copyProperties(updateDto, CrcUser.class);
        //更新用户表
        this.updateById(crcUser);

        //更新用户信息表
        CrcUserInfo crcUserInfo = BeanUtil.copyProperties(updateDto, CrcUserInfo.class);
        crcUserInfo.setUserId(updateDto.getId());
        crcUserInfo.setId(null);
        //查询是否存在
        Integer count = crcUserInfoMapper.selectCount(Wrappers.<CrcUserInfo>lambdaQuery()
                .select(CrcUserInfo::getId)
                .eq(CrcUserInfo::getUserId, updateDto.getId()));

        int r;

        if (count > 0) {
            //存在更新原数据
            r = crcUserInfoMapper.update(crcUserInfo, Wrappers.<CrcUserInfo>lambdaUpdate()
                    .eq(CrcUserInfo::getUserId, crcUserInfo.getUserId()));
        } else {
            //不存在,添加
            r = crcUserInfoMapper.insert(crcUserInfo);
        }

        return r > 0 ? PageResponseResult.okResult(200, "更新成功")
                : PageResponseResult.errorResult(500, "更新失败");
    }

    @Override
    public ResponseResult get(Long userId) {
        if (userId == null) userId=ThreadLocalUtil.getId();

        UpdateDto updateDto = crcUserMapper.getUserInfo(userId);

        return  updateDto != null ? PageResponseResult.okResult(updateDto)
                : PageResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public SimpleUserDto getSimple(Long userId) {
        SimpleUserDto simple = crcUserMapper.getSimple(userId);
        simple.setUserId(String.valueOf(userId));
        return simple;
    }

    @Resource
    private FileStorageService fileStorageService;


    @Override
    public ResponseResult uploadImg(MultipartFile multipartFile) {

        //1.检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传文件失败");
            return ResponseResult.errorResult(500,"上传文件失败");
        }

        return ResponseResult.okResult(fileId);
    }
}

