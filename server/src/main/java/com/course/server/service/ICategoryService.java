package com.course.server.service;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;

import java.util.List;


public interface ICategoryService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<CategoryDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param categoryDto
     */
    void save(CategoryDto categoryDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 查询所有
     * @return
     */
    List<CategoryDto> all();
}
