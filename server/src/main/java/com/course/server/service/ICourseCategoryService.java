package com.course.server.service;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;

import java.util.List;


public interface ICourseCategoryService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<CourseCategoryDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param courseCategoryDto
     */
    void save(CourseCategoryDto courseCategoryDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 课程批量新增分类
     *
     * @param courseId
     * @param dtoList
     */
    void saveBatch(String courseId, List<CategoryDto> dtoList);

    /**
     * 根据courseId查询课程分类
     *
     * @param courseId
     * @return
     */
    List<CourseCategoryDto> listByCourse(String courseId);
}
