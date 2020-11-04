package com.course.server.service.impl;

import com.course.server.domain.Role;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.service.IRoleService;
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
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public void list(PageDto<RoleDto> pageDto) {
        Example example = new Example(Role.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Role> roleList = roleMapper.selectByExample(example);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roleList, RoleDto.class);
        pageDto.setList(roleDtoList);
    }


    @Override
    @Transactional
    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (StringUtils.isEmpty(roleDto.getId())) {
            insert(role);
        } else {
            update(role);
        }
    }

    private void insert(Role role) {
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }

    private void update(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }


    @Override
    @Transactional
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }
}