package com.course.server.service.impl;

import com.course.server.domain.RoleUser;
import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleUserMapper;
import com.course.server.service.IRoleUserService;
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
public class RoleUserServiceImpl implements IRoleUserService {
    @Resource
    private RoleUserMapper roleUserMapper;

    @Override
    public void list(PageDto<RoleUserDto> pageDto) {
        Example example = new Example(RoleUser.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
        pageDto.setList(roleUserDtoList);
    }


    @Override
    @Transactional
    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if (StringUtils.isEmpty(roleUserDto.getId())) {
            insert(roleUser);
        } else {
            update(roleUser);
        }
    }

    private void insert(RoleUser roleUser) {
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }

    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKeySelective(roleUser);
    }


    @Override
    @Transactional
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }
}