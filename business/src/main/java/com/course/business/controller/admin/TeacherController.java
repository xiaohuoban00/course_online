package com.course.business.controller.admin;

import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/teacher")
public class TeacherController {

    @Resource
    private ITeacherService teacherService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<TeacherDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        teacherService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param teacherDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody TeacherDto teacherDto) {
        teacherService.save(teacherDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(teacherDto);
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
        teacherService.delete(id);
        return new ResponseDto();
    }
}