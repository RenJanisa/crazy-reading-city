package com.banner.admin.mapper;

import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.GetBookExcerptDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-28
 */
public interface CrcBookExcerptMapper extends BaseMapper<CrcBookExcerpt> {

}
