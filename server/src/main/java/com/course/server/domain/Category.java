package com.course.server.domain;

import javax.persistence.Id;

public class Category {
    @Id
    private String id;

    private String parent;

    private String name;

    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                '}';
    }
}