package com.banner.book.service;

import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.CrcBookAuthorSearchDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookAuthorService extends IService<CrcBookAuthor> {

    List<CrcBookAuthorSearchDto> getBookAuthorSearchList();

    ResponseResult getBookAuthorList(PageDto authorCondition);

    ResponseResult getAuthorNationality();
}
