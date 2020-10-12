package com.course.business.controller.admin;

import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.impl.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<CourseDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        courseService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param courseDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        courseService.save(courseDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        courseService.delete(id);
        return new ResponseDto();
    }
}