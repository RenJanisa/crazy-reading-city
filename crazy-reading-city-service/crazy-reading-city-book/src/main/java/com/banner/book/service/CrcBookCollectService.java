package com.banner.book.service;

import com.banner.model.book.pojos.CrcBookCollect;
import com.banner.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
public interface CrcBookCollectService extends IService<CrcBookCollect> {

    ResponseResult getIn(Long collectId);

    ResponseResult add(CrcBookCollect crcBookCollect);
}
