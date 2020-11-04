package com.course.system.controller.admin;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<RoleDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        roleService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param roleDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody RoleDto roleDto) {
        roleService.save(roleDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(roleDto);
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
        roleService.delete(id);
        return new ResponseDto();
    }
}