package com.banner.book.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.book.mapper.CrcBookMapper;
import com.banner.book.mapper.CrcBookRelationMapper;
import com.banner.book.service.CrcBookService;
import com.banner.model.book.dtos.GetBookTypeDto;
import com.banner.model.book.dtos.TypeBookDto;
import com.banner.model.book.pojos.CrcBookRelation;
import com.banner.model.book.pojos.CrcBookType;
import com.banner.book.mapper.CrcBookTypeMapper;
import com.banner.book.service.CrcBookTypeService;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.banner.model.search.dtos.GetBookSearchDto;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Service
public class CrcBookTypeServiceImpl extends ServiceImpl<CrcBookTypeMapper, CrcBookType> implements CrcBookTypeService {

    @Override
    public ResponseResult getType(String typeId) {

        if (StrUtil.isBlank(typeId)){
            //查询所有一级分类
            List<CrcBookType> list = this.list(Wrappers.<CrcBookType>lambdaQuery().eq(CrcBookType::getParentId, 0));
            return ResponseResult.okResult(list);
        }

        //查询该分类所有二级分类
        CrcBookType one = this.getOne(Wrappers.<CrcBookType>lambdaQuery().eq(CrcBookType::getId, typeId));
        List<CrcBookType> list = this.list(Wrappers.<CrcBookType>lambdaQuery().eq(CrcBookType::getParentId, typeId));
        GetBookTypeDto getBookTypeDto = BeanUtil.copyProperties(one, GetBookTypeDto.class);
        getBookTypeDto.setCrcBookTypeList(list);

        return ResponseResult.okResult(getBookTypeDto);
    }

    @Resource
    private CrcBookRelationMapper crcBookRelationMapper;
    @Resource
    private CrcBookService crcBookService;

    @Override
    public ResponseResult getTypeBook(TypeBookDto typeBookDto) {

        String typeId = typeBookDto.getTypeId();
        Integer pageSize = typeBookDto.getPageSize();
        int page = (typeBookDto.getPage() - 1) * pageSize;
        List<CrcBookSearchDto> crcBookSearchDtos;
        Integer total = 0 ;
        if (typeId != null){
            //分页查询该类型下书籍
            crcBookSearchDtos = crcBookRelationMapper.getTypeBook(typeId,page,pageSize);
            total = crcBookRelationMapper.selectCount(Wrappers.<CrcBookRelation>lambdaQuery()
                    .select(CrcBookRelation::getId).eq(CrcBookRelation::getTypeId,typeId));
        }else {
            //查询全部,推荐20本热门图书
            crcBookSearchDtos = crcBookService.getRecommendBook();
        }
        PageResponseResult pageResponseResult = new PageResponseResult(typeBookDto.getPage(),pageSize,total);
        pageResponseResult.setData(crcBookSearchDtos);

        return pageResponseResult;
    }
}
