package com.course.server.service.impl;

import com.course.server.domain.User;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.ServiceException;
import com.course.server.mapper.UserMapper;
import com.course.server.service.IUserService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void list(PageDto<UserDto> pageDto) {
        Example example = new Example(User.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
        pageDto.setList(userDtoList);
    }


    @Override
    @Transactional
    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isEmpty(userDto.getId())) {
            insert(user);
        } else {
            update(user);
        }
    }

    private void insert(User user) {
        user.setId(UuidUtil.getShortUuid());
        User userDb = selectByLoginName(user.getLoginName());
        if (userDb != null) {
            throw new ServiceException("用户名已存在");
        }
        userMapper.insertSelective(user);
    }

    private void update(User user) {
        user.setPassword(null);
        userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    @Transactional
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectByLoginName(String loginName) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("loginName", loginName);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }
}