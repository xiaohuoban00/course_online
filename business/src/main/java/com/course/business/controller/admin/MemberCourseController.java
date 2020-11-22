package com.course.business.controller.admin;

import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IMemberCourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/memberCourse")
public class MemberCourseController {

    @Resource
    private IMemberCourseService memberCourseService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<MemberCourseDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        memberCourseService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param memberCourseDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody MemberCourseDto memberCourseDto) {
        memberCourseService.save(memberCourseDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(memberCourseDto);
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
        memberCourseService.delete(id);
        return new ResponseDto();
    }
}