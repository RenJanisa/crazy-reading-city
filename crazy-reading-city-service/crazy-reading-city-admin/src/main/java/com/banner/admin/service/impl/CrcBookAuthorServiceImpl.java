package com.banner.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.banner.admin.mapper.CrcBookAuthorMapper;
import com.banner.admin.service.CrcBookAuthorService;
import com.banner.model.admin.dtos.BookAuthorListDto;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Slf4j
@Service
public class CrcBookAuthorServiceImpl extends ServiceImpl<CrcBookAuthorMapper, CrcBookAuthor> implements CrcBookAuthorService {

    @Override
    public ResponseResult addAuthor(CrcBookAuthor crcBookAuthor) {

        return this.save(crcBookAuthor) ? ResponseResult.okResult(200, "添加成功")
                : ResponseResult.errorResult(500, "添加失败");
    }

    @Override
    public ResponseResult updateAuthor(CrcBookAuthor crcBookAuthor) {

        if (crcBookAuthor.getId() == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        return this.updateById(crcBookAuthor) ? ResponseResult.okResult(200, "更新成功")
                : ResponseResult.errorResult(500, "更新失败");
    }

    @Resource
    private CrcBookAuthorMapper crcBookAuthorMapper;

    @Override
    public ResponseResult listAuthor(PageDto authorPage) {

        Integer pageSize = authorPage.getPageSize();
        int page = (authorPage.getPage() - 1) * pageSize;

        List<BookAuthorListDto> crcBookAuthors;
        PageResponseResult pageResponseResult = new PageResponseResult(authorPage.getPage(), pageSize);

        String condition = authorPage.getCondition();
        if (StrUtil.isBlank(condition)) {
            //无条件
            crcBookAuthors = crcBookAuthorMapper.listAuthor(page, pageSize);
            pageResponseResult.setTotal(crcBookAuthorMapper.selectCount(null));
            pageResponseResult.setData(crcBookAuthors);
            return pageResponseResult;
        }

        //条件查询
        String flag = condition.substring(condition.length() - 1);
        String r = "%" + condition.substring(0, condition.length() - 1) + "%";
        if (flag.equals("0")) {
            pageResponseResult.setTotal(crcBookAuthorMapper.selectCount(Wrappers.<CrcBookAuthor>lambdaQuery().like(CrcBookAuthor::getAuthorName,r)));
            crcBookAuthors = crcBookAuthorMapper.listAuthorByName(r, page, pageSize);
        } else if (flag.equals("1")) {
            pageResponseResult.setTotal(crcBookAuthorMapper.selectCount(Wrappers.<CrcBookAuthor>lambdaQuery().like(CrcBookAuthor::getNationality,r)));
            crcBookAuthors = crcBookAuthorMapper.listAuthorByNationality(r, page, pageSize);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        pageResponseResult.setData(crcBookAuthors);

        return pageResponseResult;
    }

    @Override
    public ResponseResult delAuthor(String authorIds) {

        String[] ids = authorIds.split(",");
        boolean r;
        try {
            r = crcBookAuthorMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
        } catch (Exception e) {
            return ResponseResult.errorResult(500, "该作者存在书籍,请先删除书籍");
        }
        return r ? ResponseResult.okResult(200, "删除成功") :
                ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }
}
