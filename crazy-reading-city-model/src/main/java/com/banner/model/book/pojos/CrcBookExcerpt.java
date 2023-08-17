package com.banner.model.book.pojos;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcBookExcerpt implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long bookId;

    private String title;

    private String content;

    private String thoughts;

    /**
     * 1私密,3公开已审核,2草稿,0公开未审核
     */
    private Integer status;

    /**
     * 喜欢量
     */
    private Integer likes;


    /**
     * 收藏量
     */
    private Integer collect;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;


}
