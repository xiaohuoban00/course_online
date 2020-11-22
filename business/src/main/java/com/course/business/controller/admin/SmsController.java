package com.course.business.controller.admin;

import com.course.server.dto.SmsDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ISmsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/sms")
public class SmsController {

    @Resource
    private ISmsService smsService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<SmsDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        smsService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param smsDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody SmsDto smsDto) {
        smsService.save(smsDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(smsDto);
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
        smsService.delete(id);
        return new ResponseDto();
    }
}