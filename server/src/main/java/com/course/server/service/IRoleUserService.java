package com.course.server.service;

import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;


public interface IRoleUserService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<RoleUserDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param roleUserDto
     */
    void save(RoleUserDto roleUserDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
