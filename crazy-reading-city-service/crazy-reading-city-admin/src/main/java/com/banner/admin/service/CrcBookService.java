package com.banner.admin.service;

import com.banner.model.book.dtos.AUBookDto;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.pojos.CrcBook;
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
public interface CrcBookService extends IService<CrcBook> {


    ResponseResult addBook(AUBookDto crcBook);

    ResponseResult updateBook(AUBookDto auBookDto);

    ResponseResult listBook(PageDto pageDto);

    ResponseResult delBook(String bookIds);
}
