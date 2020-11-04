package com.course.system.controller.admin;

import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IRoleUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/roleUser")
public class RoleUserController {

    @Resource
    private IRoleUserService roleUserService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<RoleUserDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        roleUserService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param roleUserDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody RoleUserDto roleUserDto) {
        roleUserService.save(roleUserDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(roleUserDto);
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
        roleUserService.delete(id);
        return new ResponseDto();
    }
}