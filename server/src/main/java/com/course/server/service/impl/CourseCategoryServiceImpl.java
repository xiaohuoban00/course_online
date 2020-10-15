package com.course.server.service.impl;

import com.course.server.domain.CourseCategory;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.service.ICourseCategoryService;
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
public class CourseCategoryServiceImpl implements ICourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public void list(PageDto<CourseCategoryDto> pageDto) {
        Example example = new Example(CourseCategory.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
        pageDto.setList(courseCategoryDtoList);
    }


    @Override
    @Transactional
    public void save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        if (StringUtils.isEmpty(courseCategoryDto.getId())) {
            insert(courseCategory);
        } else {
            update(courseCategory);
        }
    }

    private void insert(CourseCategory courseCategory) {
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKeySelective(courseCategory);
    }


    @Override
    @Transactional
    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void saveBatch(String courseId,List<CategoryDto> dtoList){
        Example example = new Example(CourseCategory.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        courseCategoryMapper.deleteByExample(example);
        CourseCategory courseCategory;
        for (CategoryDto categoryDto : dtoList) {
            courseCategory = new CourseCategory();
            courseCategory.setCategoryId(categoryDto.getId());
            courseCategory.setCourseId(courseId);
            courseCategory.setId(UuidUtil.getShortUuid());
            insert(courseCategory);
        }
    }

    @Override
    public List<CourseCategoryDto> listByCourse(String courseId){
        Example example = new Example(CourseCategory.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(courseCategoryList,CourseCategoryDto.class);
    }
}