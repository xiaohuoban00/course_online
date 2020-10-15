package com.course.server.service;

import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;


public interface ICourseService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<CourseDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param courseDto
     */
    void save(CourseDto courseDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 更新课程时长
     *
     * @param courseId
     * @return
     */
    void updateTime(String courseId);
}
