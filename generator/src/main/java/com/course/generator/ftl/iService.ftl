package com.course.server.service;

import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;

/**
 * @author zmq
 * @date 2020/10/10 5:58 下午
 */
public interface I${Domain}Service {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<${Domain}Dto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param ${domain}Dto
     */
    void save(${Domain}Dto ${domain}Dto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
