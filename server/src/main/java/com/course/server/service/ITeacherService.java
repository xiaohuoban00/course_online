package com.course.server.service;

import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;


public interface ITeacherService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<TeacherDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param teacherDto
     */
    void save(TeacherDto teacherDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
