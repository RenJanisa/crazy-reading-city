package com.banner.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.admin.mapper.CrcBookTypeMapper;
import com.banner.admin.service.CrcBookTypeService;
import com.banner.model.book.dtos.GetBookTypeDto;
import com.banner.model.book.dtos.TypeBookDto;
import com.banner.model.book.pojos.CrcBookRelation;
import com.banner.model.book.pojos.CrcBookType;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Service
public class CrcBookTypeServiceImpl extends ServiceImpl<CrcBookTypeMapper, CrcBookType> implements CrcBookTypeService {

    @Override
    public ResponseResult addType(CrcBookType crcBookType) {

        int count2 = this.count(Wrappers.<CrcBookType>lambdaQuery()
                .select(CrcBookType::getId)
                .eq(CrcBookType::getTypeName, crcBookType.getTypeName()));
        if (count2 > 0) return ResponseResult.errorResult(500, "类型重复");

        //检查是否存在父级
        if (crcBookType.getParentId() != null && crcBookType.getParentId() != 0) {
            //有,检查
            CrcBookType crcBookTypeDB = this.getOne(Wrappers.<CrcBookType>lambdaQuery()
                    .eq(CrcBookType::getId, crcBookType.getParentId()));

            if (crcBookTypeDB == null || crcBookTypeDB.getParentId() != 0)
                return ResponseResult.errorResult(500, "无效父级");

        }
        //有父级且数据库中存在父级 或 为一级分类无父级
        boolean save = this.save(crcBookType);

        return save ? PageResponseResult.okResult(200, "添加成功")
                : PageResponseResult.errorResult(500, "添加失败");
    }

}
