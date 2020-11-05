package com.course.server.mapper;

import com.course.server.domain.User;
import com.course.server.dto.ResourceDto;
import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface UserMapper extends Mapper<User> {
    List<ResourceDto> findResources(@Param("userId") String userId);
}