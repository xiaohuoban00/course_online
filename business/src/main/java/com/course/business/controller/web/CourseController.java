package com.course.business.controller.web;

import com.course.server.domain.Course;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.service.ICourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zmq
 * @date 2020/11/10 7:31 下午
 */
@RestController("webCourseController")
@RequestMapping("web/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @GetMapping("/list-new")
    public ResponseDto listNew(){
        PageDto<CourseDto> pageDto = new PageDto<>();
        pageDto.setPage(1);
        pageDto.setSize(3);
        List<CourseDto> courseDtoList = courseService.listNew(pageDto);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null,courseDtoList);
    }
}
