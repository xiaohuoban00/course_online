package com.course.system.controller.admin;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.RoleUserDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


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

    /**
     * 保存资源
     *
     * @return
     */
    @PostMapping("save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto) {
        roleService.saveResource(roleDto);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, roleDto);
    }

    /**
     * 查询已关联的资源
     *
     * @param id
     * @return
     */
    @GetMapping("list-resource/{id}")
    public ResponseDto listResource(@PathVariable String id) {
        List<String> resourceIdList = roleService.listResource(id);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, resourceIdList);
    }

    /**
     * 保存用户
     *
     * @param roleUserDto
     * @return
     */
    @PostMapping("save-user")
    public ResponseDto saveUser(@RequestBody RoleUserDto roleUserDto) {
        roleService.saveUser(roleUserDto);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, roleUserDto);
    }

    /**
     * 加载用户
     *
     * @param id
     * @return
     */
    @GetMapping("list-user/{id}")
    public ResponseDto listUser(@PathVariable String id){
        List<String> userIdList = roleService.listUser(id);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, userIdList);
    }
}