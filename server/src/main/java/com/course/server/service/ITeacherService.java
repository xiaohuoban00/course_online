package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;

import java.util.List;


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

    /**
     * 查询所有
     *
     * @return
     */
    List<TeacherDto> all();
}
