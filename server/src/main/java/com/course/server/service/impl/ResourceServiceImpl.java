package com.course.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.course.server.domain.Resource;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.service.IResourceService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;


import java.util.ArrayList;
import java.util.List;


@Service
public class ResourceServiceImpl implements IResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public void list(PageDto<ResourceDto> pageDto) {
        Example example = new Example(Resource.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }


    @Override
    @Transactional
    public void save(ResourceDto resourceDto) {
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if (StringUtils.isEmpty(resourceDto.getId())) {
            insert(resource);
        } else {
            update(resource);
        }
    }

    private void insert(Resource resource) {
        resourceMapper.insert(resource);
    }

    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKeySelective(resource);
    }


    @Override
    @Transactional
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveJson(String jsonStr) {
        List<ResourceDto> jsonList = JSON.parseArray(jsonStr, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d : jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        resourceMapper.deleteByExample(null);
        for (ResourceDto resourceDto : list) {
            insert(CopyUtil.copy(resourceDto, Resource.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     *
     * @param list
     * @param dto
     */
    private void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d : dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }

    @Override
    public List<ResourceDto> loadTree() {
        Example example = new Example(Resource.class);
        example.orderBy("id").asc();
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // 当前要移动的记录
            ResourceDto child = resourceDtoList.get(i);

            // 如果当前节点没有父节点，则不用往下了
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            // 查找父节点
            for (int j = i - 1; j >= 0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0, child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }
}