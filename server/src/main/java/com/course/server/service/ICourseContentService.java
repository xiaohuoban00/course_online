package com.course.server.service;

import com.course.server.dto.CourseContentDto;
import com.course.server.dto.PageDto;


public interface ICourseContentService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<CourseContentDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param courseContentDto
     */
    void save(CourseContentDto courseContentDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
