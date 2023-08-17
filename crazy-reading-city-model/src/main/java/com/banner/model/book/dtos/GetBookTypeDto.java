package com.banner.model.book.dtos;

import com.banner.model.book.pojos.CrcBookType;
import lombok.Data;

import java.util.List;

/**
 * @author rjj
 * @date 2023/8/2 - 15:40
 */
@Data
public class GetBookTypeDto extends CrcBookType {
    private List<CrcBookType> crcBookTypeList;
}
