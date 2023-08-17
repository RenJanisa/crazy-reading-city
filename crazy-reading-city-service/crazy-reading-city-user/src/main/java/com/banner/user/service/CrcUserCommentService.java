package com.banner.user.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.AddCRDto;
import com.banner.model.user.pojos.CrcUserComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
public interface CrcUserCommentService extends IService<CrcUserComment> {

    ResponseResult addComment(AddCRDto addCRDto);

    ResponseResult getComment(Long objId);

    ResponseResult like(String commentId,String userId);
}
