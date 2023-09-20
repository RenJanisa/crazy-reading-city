package com.banner.admin.service;

import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;

/**
 * @author rjj
 * @date 2023/9/20 - 8:46
 */
public interface CrcAdminUserService {
    ResponseResult getUserList(PageDto pageDto);
}
