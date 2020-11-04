package com.course.server.service.impl;

import com.course.server.domain.Role;
import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleUser;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.RoleUserDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.mapper.RoleUserMapper;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

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

    @Override
    @Transactional
    public void saveResource(RoleDto roleDto) {
        String id = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("roleId",id);
        roleResourceMapper.deleteByExample(example);
        RoleResource roleResource;
        for (String resourceId : resourceIds) {
            roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setResourceId(resourceId);
            roleResource.setRoleId(id);
            roleResourceMapper.insert(roleResource);
        }
    }

    @Override
    public List<String> listResource(String id) {
        Example example = new Example(RoleResource.class);
        example.createCriteria().andEqualTo("roleId",id);
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (RoleResource roleResource : roleResourceList) {
            resourceIdList.add(roleResource.getResourceId());
        }
        return resourceIdList;
    }

    @Override
    @Transactional
    public void saveUser(RoleUserDto roleUserDto) {
        String roleId = roleUserDto.getId();
        List<String> userIds = roleUserDto.getUserIds();
        Example example = new Example(RoleUser.class);
        example.createCriteria()
                .andEqualTo("roleId",roleId);
        roleResourceMapper.deleteByExample(example);
        RoleUser roleUser;
        for (String userId : userIds) {
            roleUser = new RoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);
            roleUserMapper.insert(roleUser);
        }
    }

    @Override
    public List<String> listUser(String id) {
        Example example = new Example(RoleUser.class);
        example.createCriteria().andEqualTo("roleId",id);
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        List<String> userIdList = new ArrayList<>();
        for (RoleUser roleUser : roleUserList) {
            userIdList.add(roleUser.getUserId());
        }
        return userIdList;
    }
}