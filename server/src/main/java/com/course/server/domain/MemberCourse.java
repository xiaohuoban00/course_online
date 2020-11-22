package com.course.server.domain;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class MemberCourse {
    @Id
    private String id;

    private String memberId;

    private String courseId;

    private LocalDateTime at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }

    public LocalDateTime getAt() {
        return at;
    }
}