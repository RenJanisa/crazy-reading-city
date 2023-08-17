package com.banner.model.book.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author rjj
 * @date 2023/7/28 - 8:47
 */
@Data
public class CrcBookExcerptDto {

    private Long userId;

    @NotNull(message = "书籍id不为空")
    private Long bookId;

    @Size(max = 255,message = "标题不为空")
    private String title;

    @Size(min = 20,max = 4096,message = "内容在20~4096个字之间")
    private String content;
    @Size(min = 20,max = 4096,message = "感想在20~4096个字之间")
    private String thoughts;

    /**
     * 1私密,3公开已审核,2草稿,0公开未审核,4审核未通过
     */
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
}
