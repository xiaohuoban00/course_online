package com.course.server.service;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.PageDto;

/**
 * @author zmq
 * @date 2020/10/10 5:58 下午
 */
public interface IChapterService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(ChapterPageDto pageDto);

    /**
     * 新增大章如果存在则更新
     *
     * @param chapterDto
     */
    void save(ChapterDto chapterDto);

    /**
     * 删除大章
     *
     * @param id
     */
    void delete(String id);
}
