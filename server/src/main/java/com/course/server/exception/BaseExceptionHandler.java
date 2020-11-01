package com.course.server.exception;

import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
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
        if(e instanceof ServiceException){
        	//处理的业务逻辑
            return new ResponseDto(false, CodeEnum.BAD_REQUEST.getCode(), e.getMessage(),null);
        }
        LOGGER.error(e.getMessage(),e);
        return new ResponseDto(false, CodeEnum.ERROR.getCode(), "服务器内部错误",null);
    }
}