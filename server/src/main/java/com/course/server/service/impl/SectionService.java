package com.course.server.service.impl;

import com.course.server.domain.Section;
import com.course.server.dto.SectionDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.SectionMapper;
import com.course.server.service.ISectionService;
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
public class SectionService implements ISectionService {
    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public void list(SectionPageDto pageDto) {
        Example example = new Example(Section.class);
        example.orderBy("id").desc();
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(pageDto.getChapterId())){
            criteria.andEqualTo("chapterId",pageDto.getChapterId());
        }
        if(!StringUtils.isEmpty(pageDto.getCourseId())){
            criteria.andEqualTo("courseId",pageDto.getCourseId());
        }
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Section> sectionList = sectionMapper.selectByExample(example);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        pageDto.setList(sectionDtoList);
    }


    @Override
    @Transactional
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            insert(section);
        } else {
            update(section);
        }
        courseMapper.updateTime(sectionDto.getCourseId());
    }

    private void insert(Section section) {
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insertSelective(section);
    }

    private void update(Section section) {
        sectionMapper.updateByPrimaryKeySelective(section);
    }


    @Override
    @Transactional
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}