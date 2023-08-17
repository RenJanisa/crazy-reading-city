package com.banner.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.apis.book.IBookClient;
import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.GetPlanDetailDto;
import com.banner.model.user.dtos.PlanOutDateDto;
import com.banner.model.user.pojos.CrcUserPlan;
import com.banner.model.user.pojos.CrcUserPlanDetail;
import com.banner.user.mapper.CrcUserPlanDetailMapper;
import com.banner.user.mapper.CrcUserPlanMapper;
import com.banner.user.service.CrcUserPlanDetailService;
import com.banner.user.service.CrcUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.banner.common.constants.RedisConstants.BOOK_PLAN_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
@Slf4j
@Service
public class CrcUserPlanDetailServiceImpl extends ServiceImpl<CrcUserPlanDetailMapper, CrcUserPlanDetail> implements CrcUserPlanDetailService {

    @Resource
    private CrcUserPlanDetailMapper crcUserPlanDetailMapper;

    @Resource
    private CrcUserPlanMapper crcUserPlanMapper;

    @Override
    public ResponseResult addPlanDetail(CrcUserPlanDetail crcUserPlanDetail) {

        if (crcUserPlanDetail.getBeginTime().isBefore(LocalDateTime.now())
                || crcUserPlanDetail.getEndTime().isBefore(crcUserPlanDetail.getBeginTime())) {
            return ResponseResult.errorResult(500, "时间设置错误!");
        }

        //获取已有排序
        List<Integer> ordersList = crcUserPlanDetailMapper.getPlanDetailOrders(crcUserPlanDetail.getPlanId());
        Integer orders = Collections.max(ordersList);
        System.out.println(orders);
        if (crcUserPlanDetail.getOrders() - 1 <= orders) {
            return ResponseResult.errorResult(500, "排序字段错误");
        }

        crcUserPlanDetail.setStatus(0);
        crcUserPlanDetail.setOrders(crcUserPlanDetail.getOrders() - 1);

        boolean save = this.save(crcUserPlanDetail);
        return save ? ResponseResult.okResult(200, "添加成功")
                : ResponseResult.errorResult(500, "添加失败");
    }

    @Resource
    private IBookClient bookClient;

    @Override
    public ResponseResult getPlanDetail(String planId) {

        if (StrUtil.isBlank(planId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //查询总计划
        GetPlanDetailDto getPlanDetailDto = BeanUtil.copyProperties(crcUserPlanMapper.selectById(planId), GetPlanDetailDto.class);
        //查询分计划
        getPlanDetailDto.setCrcUserPlanDetails(crcUserPlanDetailMapper.selectList(
                Wrappers.<CrcUserPlanDetail>lambdaQuery()
                        .eq(CrcUserPlanDetail::getPlanId, planId)
                        .orderByAsc(CrcUserPlanDetail::getOrders)));
        Long bookId = getPlanDetailDto.getBookId();
        //查询总计划书名和作者
        BookSimpleDto bookSimpleDto = bookClient.getBookSimple(bookId);

        getPlanDetailDto.setBookName(bookSimpleDto.getName());
        getPlanDetailDto.setNationality(bookSimpleDto.getAddress());
        getPlanDetailDto.setAuthor(bookSimpleDto.getAuthor());

        return ResponseResult.okResult(getPlanDetailDto);
    }

    @Resource
    private CrcUserService crcUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @XxlJob("crcPlanRemind")
    public void planRemind(){
        //查询所有要超时计划
       List<PlanOutDateDto> planOutDateDtos =  crcUserPlanDetailMapper.getOutDatePlan();
       if (!planOutDateDtos.isEmpty()){
           //存在需提醒计划
           for (PlanOutDateDto planOutDateDto : planOutDateDtos) {
               String value = stringRedisTemplate.opsForValue().get(BOOK_PLAN_KEY + planOutDateDto.getId());
               if (StrUtil.isBlank(value)){
                   //未提醒过
                   String content = "用户（"+planOutDateDto.getUserName()+"）您好，您的读书计划：《"+planOutDateDto.getTitle()+"》内有即将到期的任务请您记得完成!";
                   crcUserService.sendEmail(planOutDateDto.getEmail(),content);
                   log.info("提醒成功");
                   stringRedisTemplate.opsForValue().set(BOOK_PLAN_KEY+planOutDateDto.getId(),"0",30, TimeUnit.MINUTES);
               }else{
                   log.info("提醒过了");
               }
           }
       }


    }


}
