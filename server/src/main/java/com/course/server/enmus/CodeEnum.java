package com.course.server.enmus;

/**
 * @author zmq
 * @date 2020/10/14 11:45 下午
 */
public enum CodeEnum {
    SUCCESS("200"),
    BAD_REQUEST("400"),
    ERROR("500");
    private String code;

    CodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
