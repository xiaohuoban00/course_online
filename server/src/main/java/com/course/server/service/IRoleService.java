package com.course.server.service;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.RoleUserDto;

import java.util.List;


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

    /**
     * 保存资源
     *
     * @param roleDto
     */
    void saveResource(RoleDto roleDto);

    /**
     * 查询已关联的资源
     *
     * @param id
     * @return
     */
    List<String> listResource(String id);

    /**
     * 保存用户
     *
     * @param roleUserDto
     */
    void saveUser(RoleUserDto roleUserDto);

    /**
     * 加载用户
     *
     * @param id
     * @return
     */
    List<String> listUser(String id);
}
