package com.banner.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rjj
 * @date 2023/3/11 - 15:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CRCException extends RuntimeException {

    private int status;
    private String errMessage;

    public static void error(int status,String errMessage){
        throw new CRCException(status,errMessage);
    }


}
