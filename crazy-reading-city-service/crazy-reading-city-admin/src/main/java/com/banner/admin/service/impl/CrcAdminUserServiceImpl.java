package com.banner.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.banner.admin.mapper.CrcBookExcerptMapper;
import com.banner.admin.service.CrcAdminUserService;
import com.banner.apis.user.IUserClient;
import com.banner.model.admin.dtos.AdminUserListDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author rjj
 * @date 2023/9/20 - 8:46
 */
@Service
@Slf4j
public class CrcAdminUserServiceImpl implements CrcAdminUserService {

    @Resource
    private IUserClient userClient;

    @Resource
    private CrcBookExcerptMapper crcBookExcerptMapper;

    @Override
    public ResponseResult getUserList(PageDto pageDto) {

        Map adminUserListWithCount = userClient.getUserList(pageDto);

        PageResponseResult pageResponseResult = new PageResponseResult(pageDto.getPage(), pageDto.getPageSize(), (Integer) adminUserListWithCount.get("total"));
        List<AdminUserListDto> adminUserListDtos = JSONUtil.toList(adminUserListWithCount.get("adminUserListDtos").toString(),AdminUserListDto.class);
        for (AdminUserListDto adminUserListDto : adminUserListDtos) {
            adminUserListDto.setExcerptCount(crcBookExcerptMapper
                    .selectCount(Wrappers
                            .<CrcBookExcerpt>lambdaQuery()
                            .eq(CrcBookExcerpt::getUserId, adminUserListDto.getId())));
        }
        pageResponseResult.setData(adminUserListDtos);

        return pageResponseResult;
    }
}
