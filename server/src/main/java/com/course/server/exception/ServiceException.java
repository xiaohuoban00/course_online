package com.course.server.exception;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }
}