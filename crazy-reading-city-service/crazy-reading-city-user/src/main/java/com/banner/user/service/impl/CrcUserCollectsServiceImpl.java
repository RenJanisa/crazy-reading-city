package com.banner.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.CollectAddDto;
import com.banner.model.user.pojos.CrcUserCollects;
import com.banner.user.mapper.CrcUserCollectsMapper;
import com.banner.user.service.CrcUserCollectsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Service
public class CrcUserCollectsServiceImpl extends ServiceImpl<CrcUserCollectsMapper, CrcUserCollects> implements CrcUserCollectsService {

    @Override
    public ResponseResult add(CollectAddDto collectAddDto) {

        if (collectAddDto.getStatus() == null) collectAddDto.setStatus(0);
        boolean save = this.save(BeanUtil.copyProperties(collectAddDto,CrcUserCollects.class));

        return save?ResponseResult.errorResult(200,"创建成功")
                :ResponseResult.errorResult(500,"创建失败");
    }

    @Override
    public ResponseResult get(Long userId) {
        if (userId == null) userId = ThreadLocalUtil.getId();
        List<CrcUserCollects> list = this.list(Wrappers
                .<CrcUserCollects>lambdaQuery()
                .eq(CrcUserCollects::getUserId, userId));
        return ResponseResult.okResult(list);
    }

}
