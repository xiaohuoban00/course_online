package com.course.business.controller.web;

import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IMemberCourseService;
import com.course.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController("webMemberCourseController")
@RequestMapping("/web/member-course")
public class MemberCourseController {

    @Resource
    private IMemberCourseService memberCourseService;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/enroll")
    public ResponseDto enroll(@RequestBody MemberCourseDto memberCourseDto) {
        // 保存校验
        ValidatorUtil.require(memberCourseDto.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseDto.getCourseId(), "课程id");

        ResponseDto responseDto = new ResponseDto();
        memberCourseDto.setAt(LocalDateTime.now());
        memberCourseDto = memberCourseService.enroll(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/get-enroll")
    public ResponseDto getEnroll(@RequestBody MemberCourseDto memberCourseDto) {
        ResponseDto responseDto = new ResponseDto();
        memberCourseDto = memberCourseService.getEnroll(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return responseDto;
    }
}
