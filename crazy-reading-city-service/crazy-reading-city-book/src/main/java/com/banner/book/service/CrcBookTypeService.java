package com.banner.book.service;

import com.banner.model.book.dtos.TypeBookDto;
import com.banner.model.book.pojos.CrcBookType;
import com.banner.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
public interface CrcBookTypeService extends IService<CrcBookType> {


    ResponseResult getType(String typeId);

    ResponseResult getTypeBook(TypeBookDto typeBookDto);
}
