package com.course.server.dto;

/**
 * @author zmq
 * @date 2020/10/12 10:11 下午
 */
public class SectionPageDto extends PageDto{
    private String chapterId;

    private String courseId;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
