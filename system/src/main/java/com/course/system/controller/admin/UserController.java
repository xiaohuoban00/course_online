package com.course.system.controller.admin;

import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IUserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("admin/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<UserDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        userService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param userDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        userService.save(userDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(userDto);
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
        userService.delete(id);
        return new ResponseDto();
    }

    /**
     * 重置密码
     *
     * @param userDto
     * @return
     */
    @PostMapping("save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        userService.savePassword(userDto);
        return new ResponseDto();
    }

}