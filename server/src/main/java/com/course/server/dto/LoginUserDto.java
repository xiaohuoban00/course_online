package com.course.server.dto;

import java.util.List;
import java.util.Set;

/**
 * @author zmq
 * @date 2020/11/1 3:13 下午
 */
public class LoginUserDto {
    private String id;

    private String loginName;

    private String name;

    private String token;

    /**
     * 所有资源，用于界面的控制
     */
    private List<ResourceDto> resources;

    /**
     * 资源中所有的请求
     */
    private Set<String> requests;

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public Set<String> getRequests() {
        return requests;
    }

    public void setRequests(Set<String> requests) {
        this.requests = requests;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
