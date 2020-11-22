package com.course.business.controller.web;

import com.course.server.dto.ResponseDto;
import com.course.server.dto.SmsDto;
import com.course.server.service.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("webSmsController")
@RequestMapping("/web/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);

    @Resource
    private ISmsService smsService;

    @PostMapping("send")
    public ResponseDto send(@RequestBody SmsDto smsDto) {
        LOG.info("发送短信请求开始: {}", smsDto);
        ResponseDto responseDto = new ResponseDto();
        smsService.sendCode(smsDto);
        LOG.info("发送短信请求结束");
        return responseDto;
    }
}
