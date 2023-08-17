package com.banner.user.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.CollectAddDto;
import com.banner.model.user.pojos.CrcUserCollects;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
public interface CrcUserCollectsService extends IService<CrcUserCollects> {

    ResponseResult add(CollectAddDto collectAddDto);

    ResponseResult get(Long userId);
}
