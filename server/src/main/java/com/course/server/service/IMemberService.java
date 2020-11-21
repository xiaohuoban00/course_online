package com.course.server.service;

import com.course.server.dto.LoginMemberDto;
import com.course.server.dto.MemberDto;
import com.course.server.dto.PageDto;


public interface IMemberService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<MemberDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param memberDto
     */
    void save(MemberDto memberDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 按手机号查找
     *
     * @param mobile
     * @return
     */
    MemberDto findByMobile(String mobile);

    /**
     * 登录
     * @param memberDto
     */
    LoginMemberDto login(MemberDto memberDto);

    /**
     * 重置密码
     */
    void resetPassword(MemberDto memberDto);
}
