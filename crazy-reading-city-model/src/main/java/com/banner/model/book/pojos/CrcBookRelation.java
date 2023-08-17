package com.banner.model.book.pojos;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcBookRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 类型id
     */
    private Integer typeId;

    /**
     * 书籍id
     */
    private Long bookId;


}
