package com.banner.book.service;

import com.banner.model.book.dtos.CrcBookExcerptDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.banner.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-28
 */
public interface CrcBookExcerptService extends IService<CrcBookExcerpt> {

    ResponseResult addExcerpt(CrcBookExcerptDto crcBookExcerptDto);

    ResponseResult getExcerpt(Long excerptId);

    ResponseResult like(String excerptId, String userId);
}
