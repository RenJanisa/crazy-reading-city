package com.banner.search.service;

import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;

/**
 * @author rjj
 * @date 2023/8/9 - 20:22
 */
public interface SearchService {

    ResponseResult searchBookByName(PageDto getBookSearchDto);

    ResponseResult searchBookAuthorByName(String authorName);

    ResponseResult getSearchHistory(String userId);

    ResponseResult delSearchHistory(String userId);
}
