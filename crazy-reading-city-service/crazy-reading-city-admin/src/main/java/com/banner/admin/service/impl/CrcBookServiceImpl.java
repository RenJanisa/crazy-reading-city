package com.banner.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.banner.admin.mapper.CrcBookMapper;
import com.banner.admin.mapper.CrcBookRelationMapper;
import com.banner.admin.service.CrcBookService;
import com.banner.model.admin.dtos.AdminBookListDto;
import com.banner.model.book.dtos.AUBookDto;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.book.pojos.CrcBookRelation;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    @Resource
    private CrcBookRelationMapper crcBookRelationMapper;

    @Override
    @Transactional
    public ResponseResult addBook(AUBookDto crcBook) {

        int count = this.count(Wrappers.<CrcBook>lambdaQuery()
                .select(CrcBook::getId)
                .eq(CrcBook::getBookName, crcBook.getBookName()));
        if (count > 0) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);

        long bookId = IdWorker.getId();
        crcBook.setId(bookId);
        boolean save = this.save(crcBook);
        if (!save) return ResponseResult.errorResult(500, "添加失败");

        //添加书籍与类型的关系
        List<CrcBookRelation> crcBookRelations = crcBook.getTypeId().stream()
                .map(i -> CrcBookRelation.builder().bookId(bookId).typeId(i).build()).collect(Collectors.toList());

        int insert = 0;
        for (CrcBookRelation crcBookRelation : crcBookRelations) {
            insert = insert + crcBookRelationMapper.insert(crcBookRelation);
        }
        return insert == crcBookRelations.size() ? ResponseResult.okResult(200, "添加成功")
                : ResponseResult.errorResult(500, "添加失败");
    }

    @Override
    public ResponseResult updateBook(AUBookDto auBookDto) {

        boolean update = this.updateById(auBookDto);
        if (!update) return ResponseResult.errorResult(500, "更新失败");

        //删除所有旧的类型关系
        crcBookRelationMapper.delete(Wrappers.<CrcBookRelation>lambdaQuery().eq(CrcBookRelation::getBookId, auBookDto.getId()));

        //添加更新后的
        List<CrcBookRelation> crcBookRelations = auBookDto.getTypeId().stream()
                .map(i -> CrcBookRelation.builder().bookId(auBookDto.getId()).typeId(i).build()).collect(Collectors.toList());

        int insert = 0;
        for (CrcBookRelation crcBookRelation : crcBookRelations) {
            insert = insert + crcBookRelationMapper.insert(crcBookRelation);
        }

        return insert == crcBookRelations.size() ? ResponseResult.okResult(200, "更新成功")
                : ResponseResult.errorResult(500, "更新失败");
    }

    @Resource
    private CrcBookMapper crcBookMapper;

    @Override
    public ResponseResult listBook(PageDto pageDto) {

        String condition = pageDto.getCondition();
        Integer pageSize = pageDto.getPageSize();
        int page = (pageDto.getPage() - 1) * pageSize;

        List<AdminBookListDto> bookList;
        PageResponseResult pageResponseResult = new PageResponseResult(pageDto.getPage(), pageSize);

        if (StrUtil.isBlank(condition)) {
            //无条件
            bookList = crcBookMapper.listBook(page,pageSize);
            pageResponseResult.setTotal(crcBookMapper.selectCount(null));
            pageResponseResult.setData(bookList);
            return pageResponseResult;

        }
        //存在条件
        String bookName = "%" + condition + "%";
        bookList = crcBookMapper.listBookByName(bookName, page, pageSize);
        pageResponseResult.setTotal(crcBookMapper.selectCount(Wrappers.<CrcBook>lambdaQuery().like(CrcBook::getBookName,bookName)));
        pageResponseResult.setData(bookList);

        return pageResponseResult;
    }

    @Override
    public ResponseResult delBook(String bookIds) {
        String[] ids = bookIds.split(",");
        boolean r;
        try {
            r = crcBookMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
        } catch (Exception e) {
            return ResponseResult.errorResult(500, "该书籍存在关联内容(摘录或计划),无法删除");
        }
        return r ? ResponseResult.okResult(200, "删除成功") :
                ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }
}
