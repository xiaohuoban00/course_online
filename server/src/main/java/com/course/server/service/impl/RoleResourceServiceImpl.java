package com.course.server.service.impl;

import com.course.server.domain.RoleResource;
import com.course.server.dto.RoleResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.service.IRoleResourceService;
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
public class RoleResourceServiceImpl implements IRoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Override
    public void list(PageDto<RoleResourceDto> pageDto) {
        Example example = new Example(RoleResource.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RoleResourceDto.class);
        pageDto.setList(roleResourceDtoList);
    }


    @Override
    @Transactional
    public void save(RoleResourceDto roleResourceDto) {
        RoleResource roleResource = CopyUtil.copy(roleResourceDto, RoleResource.class);
        if (StringUtils.isEmpty(roleResourceDto.getId())) {
            insert(roleResource);
        } else {
            update(roleResource);
        }
    }

    private void insert(RoleResource roleResource) {
        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }

    private void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKeySelective(roleResource);
    }


    @Override
    @Transactional
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }
}