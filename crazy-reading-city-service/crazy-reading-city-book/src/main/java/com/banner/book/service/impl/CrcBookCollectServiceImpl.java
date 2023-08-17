package com.banner.book.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.banner.apis.user.IUserClient;
import com.banner.book.mapper.CrcBookExcerptMapper;
import com.banner.book.mapper.CrcBookMapper;
import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.CrcCollectsDto;
import com.banner.model.book.pojos.CrcBookCollect;
import com.banner.book.mapper.CrcBookCollectMapper;
import com.banner.book.service.CrcBookCollectService;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.SimpleUserDto;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Service
public class CrcBookCollectServiceImpl extends ServiceImpl<CrcBookCollectMapper, CrcBookCollect> implements CrcBookCollectService {

    @Resource
    private CrcBookMapper crcBookMapper;

    @Resource
    private CrcBookExcerptMapper crcBookExcerptMapper;

    @Resource
    private IUserClient userClient;

    @Override
    public ResponseResult getIn(Long collectId) {
        List<CrcBookCollect> list = this.list(Wrappers.<CrcBookCollect>lambdaQuery().eq(CrcBookCollect::getCollectId, collectId));

        if (list.isEmpty()) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);

        List<CrcCollectsDto> result = list.stream().map(i -> {
            CrcCollectsDto crcCollectsDto = BeanUtil.copyProperties(i, CrcCollectsDto.class);
            BookSimpleDto bookSimpleDto;
            if (crcCollectsDto.getFlag() == 0) {
                //收藏书籍,查询作者和国籍
                bookSimpleDto = crcBookMapper.getNameAndAuthor(crcCollectsDto.getObjId());
            } else{
                //收藏摘录,查询作者和头像
                bookSimpleDto = crcBookExcerptMapper.getNameAndAuthor(crcCollectsDto.getObjId());
                SimpleUserDto simpleUserDto = userClient.get(bookSimpleDto.getAuthorId());
                bookSimpleDto.setAuthor(simpleUserDto.getUserName());
                bookSimpleDto.setAddress(simpleUserDto.getAvatar());
            }
            BeanUtil.copyProperties(bookSimpleDto,crcCollectsDto);
            return crcCollectsDto;
        }).collect(Collectors.toList());

        return ResponseResult.okResult(result);
    }

    @Override
    @Transactional
    public ResponseResult add(CrcBookCollect crcBookCollect) {
        boolean save = this.save(crcBookCollect);
        if (crcBookCollect.getFlag() == 0){
            crcBookMapper.updateCollect(crcBookCollect.getObjId());
        }else {
            crcBookExcerptMapper.updateCollect(crcBookCollect.getObjId());
        }
        return save ? PageResponseResult.okResult(200, "添加成功")
                : PageResponseResult.errorResult(500, "添加失败");
    }
}
