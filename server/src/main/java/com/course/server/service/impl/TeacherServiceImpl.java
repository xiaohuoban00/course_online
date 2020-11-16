package com.course.server.service.impl;

import com.course.server.domain.Teacher;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
import com.course.server.service.ITeacherService;
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
public class TeacherServiceImpl implements ITeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public void list(PageDto<TeacherDto> pageDto) {
        Example example = new Example(Teacher.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Teacher> teacherList = teacherMapper.selectByExample(example);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        pageDto.setList(teacherDtoList);
    }


    @Override
    @Transactional
    public void save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if (StringUtils.isEmpty(teacherDto.getId())) {
            insert(teacher);
        } else {
            update(teacher);
        }
    }

    private void insert(Teacher teacher) {
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKeySelective(teacher);
    }


    @Override
    @Transactional
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TeacherDto> all() {
        Example example = new Example(Teacher.class);
        example.orderBy("id").desc();
        List<Teacher> teacherList = teacherMapper.selectByExample(example);
        return CopyUtil.copyList(teacherList,TeacherDto.class);
    }

    @Override
    public TeacherDto findById(String teacherId) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
        return CopyUtil.copy(teacher,TeacherDto.class);
    }
}