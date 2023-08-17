package com.banner.search.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.GetBookSearchDto;

/**
 * @author rjj
 * @date 2023/8/9 - 20:22
 */
public interface BookSearchService {


    ResponseResult searchByName(GetBookSearchDto getBookSearchDto);
}
