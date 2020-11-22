package com.course.server.service;

import com.course.server.dto.SmsDto;
import com.course.server.dto.PageDto;


public interface ISmsService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<SmsDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param smsDto
     */
    void save(SmsDto smsDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 发送验证码
     *
     * @param smsDto
     */
    void sendCode(SmsDto smsDto);

    /**
     * 校验验证码
     *
     * @param smsDto
     */
    void validCode(SmsDto smsDto);
}
