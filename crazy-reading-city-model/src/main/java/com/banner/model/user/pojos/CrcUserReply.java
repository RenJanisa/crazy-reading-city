package com.banner.model.user.pojos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcUserReply implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 二级回复id
     */
    private Long replierId;

    private Long userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 喜欢数量
     */
    private Integer likes;


    /**
     * 发布时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
