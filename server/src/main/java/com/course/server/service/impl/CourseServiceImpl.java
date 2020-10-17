package com.course.server.service.impl;

import com.course.server.domain.Course;
import com.course.server.domain.CourseContent;
import com.course.server.dto.*;
import com.course.server.mapper.CategoryMapper;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.service.ICourseCategoryService;
import com.course.server.service.ICourseService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class CourseServiceImpl implements ICourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ICourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    @Override
    public void list(PageDto<CourseDto> pageDto) {
        Example example = new Example(Course.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Course> courseList = courseMapper.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);
        pageDto.setList(courseDtoList);
    }


    @Override
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        LocalDateTime now = LocalDateTime.now();
        course.setUpdatedAt(now);
        if (StringUtils.isEmpty(courseDto.getId())) {
            course.setCreatedAt(now);
            insert(course);
        } else {
            update(course);
        }
        courseCategoryService.saveBatch(course.getId(),courseDto.getCategorys());
    }

    private void insert(Course course) {
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    private void update(Course course) {
        courseMapper.updateByPrimaryKeySelective(course);
    }


    @Override
    @Transactional
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTime(String courseId) {
        courseMapper.updateTime(courseId);
    }

    @Override
    public CourseContentDto findContent(String id){
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(id);
        if(courseContent==null){
            return null;
        }
        return CopyUtil.copy(courseContent,CourseContentDto.class);
    }

    @Override
    public int saveContent(CourseContentDto courseContentDto){
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeySelective(courseContent);
        if(i==0){
            i = courseContentMapper.insert(courseContent);
        }
        return i;
    }

    @Override
    @Transactional
    public void sort(SortDto sortDto) {
        courseMapper.updateSort(sortDto);
        //如果排序值变大
        if(sortDto.getNewSort()>sortDto.getOldSort()){
            courseMapper.moveSortsForward(sortDto);
        }
        //如果排序值变少
        if(sortDto.getNewSort()<sortDto.getOldSort()){
            courseMapper.moveSortsBackward(sortDto);
        }
    }

}