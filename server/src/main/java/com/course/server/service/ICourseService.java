package com.course.server.service;

import com.course.server.dto.*;

import java.util.List;


public interface ICourseService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(CoursePageDto pageDto);

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

    /**
     * 查询课程内容
     *
     * @param id
     * @return
     */
    CourseContentDto findContent(String id);

    /**
     * 保存或更新课程内容
     *
     * @param courseContentDto
     * @return
     */
    int saveContent(CourseContentDto courseContentDto);

    /**
     * 排序
     *
     * @param sortDto
     */
    void sort(SortDto sortDto);

    /**
     * 新课列表查询，只查3条
     *
     * @param pageDto
     * @return
     */
    List<CourseDto> listNew(PageDto<CourseDto> pageDto);

    /**
     * 查找某一课程，供web模块用，只能查已发布的
     *
     * @param id
     * @return
     */
    CourseDto findCourse(String id);
}
