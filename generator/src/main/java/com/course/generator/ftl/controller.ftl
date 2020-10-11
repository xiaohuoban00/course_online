package com.course.business.controller.admin;

import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.impl.${Domain}Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/${domain}")
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<${Domain}Dto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        ${domain}Service.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param ${domain}Dto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto) {
        ${domain}Service.save(${domain}Dto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(${domain}Dto);
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
        ${domain}Service.delete(id);
        return new ResponseDto();
    }
}