package com.banner.model.book.dtos;

import com.banner.model.book.pojos.CrcBook;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author rjj
 * @date 2023/8/18 - 15:34
 */
@Data
public class AUBookDto extends CrcBook {
    @NotNull(message = "数据类型不为空")
    private List<Integer> typeId;
}
