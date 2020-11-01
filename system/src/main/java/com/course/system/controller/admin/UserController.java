package com.course.system.controller.admin;

import com.course.server.domain.User;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.service.IUserService;
import com.course.server.utils.ValidatorUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("admin/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

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

    @PostMapping("login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request) {
        ValidatorUtil.require(userDto.getLoginName(), "用户名");
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        String imageCode = redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        if(StringUtils.isEmpty(imageCode)){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            return responseDto;
        }
        if(!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不正确");
        }else {
            LoginUserDto dto = userService.login(userDto);
            request.getSession().setAttribute(User.LOGIN_USER,dto);
            responseDto.setSuccess(true);
            responseDto.setContent(dto);
            request.getSession().removeAttribute(userDto.getImageCodeToken());
        }
        return responseDto;
    }

    @GetMapping("logout")
    public ResponseDto logout(HttpServletRequest request){
        request.getSession().removeAttribute(User.LOGIN_USER);
        return new ResponseDto();
    }

}