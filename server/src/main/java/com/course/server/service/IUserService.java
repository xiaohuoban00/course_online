package com.course.server.service;

import com.course.server.domain.User;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;


public interface IUserService {

    /**
     * 查询列表
     *
     * @param pageDto
     */
    void list(PageDto<UserDto> pageDto);

    /**
     * 新增,如存在则更新
     *
     * @param userDto
     */
    void save(UserDto userDto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 根据登录名查询用户
     *
     * @param loginName
     * @return
     */
    User selectByLoginName(String loginName);

    /**
     * 保存密码
     *
     * @param userDto
     */
    void savePassword(UserDto userDto);

    /**
     * 登录
     *
     * @param userDto
     * @return
     */
    LoginUserDto login(UserDto userDto);
}
