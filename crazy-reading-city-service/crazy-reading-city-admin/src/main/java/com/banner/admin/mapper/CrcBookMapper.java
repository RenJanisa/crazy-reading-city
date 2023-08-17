package com.banner.admin.mapper;

import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.CrcBookInfoDto;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookMapper extends BaseMapper<CrcBook> {


}
