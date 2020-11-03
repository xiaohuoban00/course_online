package com.course.server.service;

import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;

import java.util.List;


public interface IResourceService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<ResourceDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param resourceDto
     */
    void save(ResourceDto resourceDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 保存资源树
     *
     * @param jsonStr
     */
    void saveJson(String jsonStr);

    /**
     * 加载资源树
     *
     * @return
     */
    List<ResourceDto> loadTree();
}
