package com.course.server.service;

import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;


public interface IFileService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<FileDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param fileDto
     */
    void save(FileDto fileDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 根据文件标识查询数据库记录
     *
     * @param key
     * @return
     */
    FileDto findByKey(String key);
}
