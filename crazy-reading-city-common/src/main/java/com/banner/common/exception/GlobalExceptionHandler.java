package com.banner.common.exception;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author rjj
 * @date 2023/2/12 - 15:25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //处理自定义异常
    @ExceptionHandler(CRCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult customException(CRCException e){
        log.error("【业务异常】{}",e.getErrMessage(),e);
        return  ResponseResult.errorResult(e.getStatus(),e.getErrMessage());
    }

    /**
     * jsr303检验错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult doMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        //校验错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //收集错误信息
        StringBuffer errors = new StringBuffer();
        fieldErrors.forEach((item)->{
            errors.append(item.getDefaultMessage()).append(',');
        });

        return ResponseResult.errorResult(500, errors.toString());
    }


    //处理未知异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult Exception(Exception e){
        log.error("【系统异常】{}",e.getMessage(),e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }
}
