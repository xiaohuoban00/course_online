package com.course.server.dto;

/**
 * @author zmq
 * @date 2020/10/12 9:46 下午
 */
public class ChapterPageDto extends PageDto {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
