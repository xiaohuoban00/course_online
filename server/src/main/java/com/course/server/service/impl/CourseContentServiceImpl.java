package com.course.server.service.impl;

import com.course.server.domain.CourseContent;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.service.ICourseContentService;
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
public class CourseContentServiceImpl implements ICourseContentService {
    @Resource
    private CourseContentMapper courseContentMapper;

    @Override
    public void list(PageDto<CourseContentDto> pageDto) {
        Example example = new Example(CourseContent.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseContent> courseContentList = courseContentMapper.selectByExample(example);
        PageInfo<CourseContent> pageInfo = new PageInfo<>(courseContentList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseContentDto> courseContentDtoList = CopyUtil.copyList(courseContentList, CourseContentDto.class);
        pageDto.setList(courseContentDtoList);
    }


    @Override
    @Transactional
    public void save(CourseContentDto courseContentDto) {
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        if (StringUtils.isEmpty(courseContentDto.getId())) {
            insert(courseContent);
        } else {
            update(courseContent);
        }
    }

    private void insert(CourseContent courseContent) {
        courseContent.setId(UuidUtil.getShortUuid());
        courseContentMapper.insert(courseContent);
    }

    private void update(CourseContent courseContent) {
        courseContentMapper.updateByPrimaryKeySelective(courseContent);
    }


    @Override
    @Transactional
    public void delete(String id) {
        courseContentMapper.deleteByPrimaryKey(id);
    }
}