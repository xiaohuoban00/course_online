package com.course.server.dto;

/**
 * @author zmq
 * @date 2020/11/1 3:13 下午
 */
public class LoginUserDto {
    private String id;

    private String loginName;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
