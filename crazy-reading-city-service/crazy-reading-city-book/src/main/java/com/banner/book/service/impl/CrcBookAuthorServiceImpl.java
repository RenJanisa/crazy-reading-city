package com.banner.book.service.impl;

import cn.hutool.core.util.StrUtil;
import com.banner.book.mapper.CrcBookAuthorMapper;
import com.banner.book.service.CrcBookAuthorService;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.CrcBookAuthorSearchDto;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Service
public class CrcBookAuthorServiceImpl extends ServiceImpl<CrcBookAuthorMapper, CrcBookAuthor> implements CrcBookAuthorService {

    @Resource
    private CrcBookAuthorMapper crcBookAuthorMapper;

    @Override
    public List<CrcBookAuthorSearchDto> getBookAuthorSearchList() {
        return crcBookAuthorMapper.getBookAuthorList();
    }

    @Override
    public ResponseResult getBookAuthorList(PageDto authorCondition) {
        String nationality = authorCondition.getCondition();
        if (StrUtil.isBlank(nationality)) nationality = "中国";
        Integer pageSize = authorCondition.getPageSize();
        Integer page = (authorCondition.getPage() - 1) * pageSize;

        List<CrcBookAuthorSearchDto> list = crcBookAuthorMapper.getBookAuthorListByCondition(nationality, page, pageSize);
        int total = this.count(Wrappers.<CrcBookAuthor>lambdaQuery().eq(CrcBookAuthor::getNationality, nationality));
        PageResponseResult pageResponseResult = new PageResponseResult(authorCondition.getPage(), pageSize, total);
        pageResponseResult.setData(list);
        return pageResponseResult;
    }

    @Override
    public ResponseResult getAuthorNationality() {
        List<Map> map = crcBookAuthorMapper.getBookAuthorNationality();
        return ResponseResult.okResult(map);
    }
}
