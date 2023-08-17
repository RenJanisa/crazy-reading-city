package com.banner.user.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.AddCRDto;
import com.banner.model.user.dtos.GetUserReplyDto;
import com.banner.model.user.dtos.GetUserReplyPlusDto;
import com.banner.model.user.pojos.CrcUserReply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
public interface CrcUserReplyService extends IService<CrcUserReply> {

    ResponseResult addReply(AddCRDto addCRDto);

    List<GetUserReplyDto> listById(Long id);

    List<GetUserReplyPlusDto> listPlusById(Long id);

    ResponseResult like(String replyId, String userId);
}
