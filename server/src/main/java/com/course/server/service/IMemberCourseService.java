package com.course.server.service;

import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.PageDto;


public interface IMemberCourseService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<MemberCourseDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param memberCourseDto
     */
    void save(MemberCourseDto memberCourseDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 获取报名信息
     *
     * @param memberCourseDto
     * @return
     */
    MemberCourseDto getEnroll(MemberCourseDto memberCourseDto);

    /**
     * 报名，先判断是否已报名
     *
     * @param memberCourseDto
     */
    MemberCourseDto enroll(MemberCourseDto memberCourseDto);
}
