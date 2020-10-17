package com.course.server.mapper;

import com.course.server.domain.Course;
import com.course.server.dto.SortDto;
import tk.mybatis.mapper.common.Mapper;


public interface CourseMapper extends Mapper<Course> {

    void updateTime(String courseId);

    void updateSort(SortDto sortDto);

    void moveSortsForward(SortDto sortDto);

    void moveSortsBackward(SortDto sortDto);
}