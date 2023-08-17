package com.banner.admin.service.impl;

import com.banner.admin.mapper.CrcBookAuthorMapper;
import com.banner.admin.service.CrcBookAuthorService;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Service
public class CrcBookAuthorServiceImpl extends ServiceImpl<CrcBookAuthorMapper, CrcBookAuthor> implements CrcBookAuthorService {

    @Override
    public ResponseResult addAuthor(CrcBookAuthor crcBookAuthor) {

        return this.save(crcBookAuthor) ? ResponseResult.okResult(200, "添加成功")
                : ResponseResult.errorResult(500, "添加失败");
    }
}
