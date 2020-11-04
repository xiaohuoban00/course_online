package com.course.system.controller.admin;

import com.course.server.dto.RoleResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IRoleResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/roleResource")
public class RoleResourceController {

    @Resource
    private IRoleResourceService roleResourceService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<RoleResourceDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        roleResourceService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param roleResourceDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody RoleResourceDto roleResourceDto) {
        roleResourceService.save(roleResourceDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(roleResourceDto);
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
        roleResourceService.delete(id);
        return new ResponseDto();
    }
}