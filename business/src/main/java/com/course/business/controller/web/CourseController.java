package com.course.business.controller.web;

import com.course.server.domain.Course;
import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.enmus.CourseStatusEnum;
import com.course.server.service.ICourseService;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDto listNew() {
        PageDto<CourseDto> pageDto = new PageDto<>();
        pageDto.setPage(1);
        pageDto.setSize(3);
        List<CourseDto> courseDtoList = courseService.listNew(pageDto);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, courseDtoList);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody CoursePageDto pageDto) {
        pageDto.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.list(pageDto);
        return new ResponseDto(true,CodeEnum.SUCCESS.getCode(), null,pageDto);
    }

    @GetMapping("/find/{id}")
    public ResponseDto findCourse(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        CourseDto courseDto = courseService.findCourse(id);
        responseDto.setContent(courseDto);
        return responseDto;
    }
}
