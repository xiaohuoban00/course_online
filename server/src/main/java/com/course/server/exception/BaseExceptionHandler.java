package com.course.server.exception;

import com.course.server.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class BaseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDto handler(Exception e){
        if(e instanceof ValidatorException){
        	//处理的业务逻辑
            LOGGER.warn(e.getMessage());
            return new ResponseDto(false,"400","请求参数异常",null);
        }
        e.printStackTrace();
        return new ResponseDto(false, "500","服务器内部错误",null);
    }
}