package com.course.server.service;

import com.course.server.dto.RoleResourceDto;
import com.course.server.dto.PageDto;


public interface IRoleResourceService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<RoleResourceDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param roleResourceDto
     */
    void save(RoleResourceDto roleResourceDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
