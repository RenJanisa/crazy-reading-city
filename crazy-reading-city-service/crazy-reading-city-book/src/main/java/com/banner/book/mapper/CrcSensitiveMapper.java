package com.banner.book.mapper;

import com.banner.model.book.pojos.CrcSensitive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author rjj
 * @date 2023/7/28 - 10:16
 */
public interface CrcSensitiveMapper extends BaseMapper<CrcSensitive> {
    @Select("SELECT cs.sensitives FROM crc_sensitive cs")
    List<String> getCrcSensitiveList();
}
