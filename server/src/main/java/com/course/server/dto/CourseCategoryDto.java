package com.course.server.dto;

import javax.persistence.Id;

/**
 * @author zmq
 * @date 2020/10/16 12:01 上午
 */
public class CourseCategoryDto {
    private String id;

    private String courseId;

    private String categoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CourseCategory{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
