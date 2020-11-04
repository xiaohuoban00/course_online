package com.course.server.domain;

import javax.persistence.Column;
import javax.persistence.Id;

public class Role {
    @Id
    private String id;

    private String name;

    @Column(name = "`desc`")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}