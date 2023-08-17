package com.banner.admin.service;

import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookAuthorService extends IService<CrcBookAuthor> {

    ResponseResult addAuthor(CrcBookAuthor crcBookAuthor);
}
