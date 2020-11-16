package com.course.server.mapper;

import com.course.server.domain.Course;
import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.dto.SortDto;
import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface CourseMapper extends Mapper<Course> {

    void updateTime(String courseId);

    void updateSort(SortDto sortDto);

    void moveSortsForward(SortDto sortDto);

    void moveSortsBackward(SortDto sortDto);

    List<CourseDto> list(CoursePageDto pageDto);
}