package com.course.server.service;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;


public interface IRoleService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<RoleDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param roleDto
     */
    void save(RoleDto roleDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
