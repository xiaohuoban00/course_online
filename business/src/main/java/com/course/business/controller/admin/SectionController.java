package com.course.business.controller.admin;

import com.course.server.dto.SectionDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.service.impl.SectionService;
import com.course.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/section")
public class SectionController {

    @Resource
    private SectionService sectionService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody SectionPageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(pageDto.getChapterId(),"大章id");
        ValidatorUtil.require(pageDto.getCourseId(),"课程id");
        responseDto.setContent(pageDto);
        sectionService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param sectionDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody SectionDto sectionDto) {
        ValidatorUtil.require(sectionDto.getChapterId(),"大章Id");
        ValidatorUtil.require(sectionDto.getCourseId(),"课程Id");
        sectionService.save(sectionDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(sectionDto);
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
        sectionService.delete(id);
        return new ResponseDto();
    }
}