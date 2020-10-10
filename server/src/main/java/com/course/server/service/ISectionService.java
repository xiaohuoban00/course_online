package com.course.server.service;

import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;

/**
 * @author zmq
 * @date 2020/10/10 5:58 下午
 */
public interface ISectionService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<SectionDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param sectionDto
     */
    void save(SectionDto sectionDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
