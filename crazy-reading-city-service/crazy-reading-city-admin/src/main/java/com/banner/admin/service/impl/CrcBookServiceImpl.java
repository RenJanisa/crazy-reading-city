package com.banner.admin.service.impl;

import com.banner.admin.mapper.CrcBookMapper;
import com.banner.admin.service.CrcBookService;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Service
public class CrcBookServiceImpl extends ServiceImpl<CrcBookMapper, CrcBook> implements CrcBookService {


    @Override
    public ResponseResult addBook(CrcBook crcBook) {

        int count = this.count(Wrappers.<CrcBook>lambdaQuery()
                .select(CrcBook::getId)
                .eq(CrcBook::getBookName, crcBook.getBookName()));
        if (count > 0) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);

        return this.save(crcBook) ? ResponseResult.okResult(200, "添加成功")
                : ResponseResult.errorResult(500, "添加失败");
    }
}
