package com.imooc.ecommerce.advice;

import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

    /*
    * 全局异常的捕获处理
    * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice{

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handleCommerceException(
            HttpServletRequest req, Exception ex
    ){
        CommonResponse<String> response = new CommonResponse<>(-1,"business error");
        response.setData(ex.getMessage());
        log.info("commerce service has error:[{}]",ex.getMessage(),ex);
        return response;
    }
}
