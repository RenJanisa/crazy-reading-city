package com.banner.model.book.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author rjj
 * @date 2023/8/3 - 15:34
 */
@Data
public class GetBookExcerptDto {

    private Long id;

    private Long userId;

    private String userName;

    private String avatar;

    private String title;

    private Integer likes;

    /**
     * 收藏量
     */
    private Integer collect;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;


}
