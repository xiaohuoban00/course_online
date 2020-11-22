package com.course.server.service.impl;

import com.course.server.domain.MemberCourse;
import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.MemberCourseMapper;
import com.course.server.service.IMemberCourseService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MemberCourseServiceImpl implements IMemberCourseService {
    @Resource
    private MemberCourseMapper memberCourseMapper;

    @Override
    public void list(PageDto<MemberCourseDto> pageDto) {
        Example example = new Example(MemberCourse.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(example);
        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberCourseDto> memberCourseDtoList = CopyUtil.copyList(memberCourseList, MemberCourseDto.class);
        pageDto.setList(memberCourseDtoList);
    }


    @Override
    @Transactional
    public void save(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
        if (StringUtils.isEmpty(memberCourseDto.getId())) {
            insert(memberCourse);
        } else {
            update(memberCourse);
        }
    }

    private void insert(MemberCourse memberCourse) {
        memberCourse.setId(UuidUtil.getShortUuid());
        memberCourseMapper.insertSelective(memberCourse);
    }

    private void update(MemberCourse memberCourse) {
        memberCourseMapper.updateByPrimaryKeySelective(memberCourse);
    }


    @Override
    @Transactional
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 报名，先判断是否已报名
     *
     * @param memberCourseDto
     */
    @Override
    public MemberCourseDto enroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourseDb = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        if (memberCourseDb == null) {
            MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
            this.insert(memberCourse);
            // 将数据库信息全部返回，包括id, at
            return CopyUtil.copy(memberCourse, MemberCourseDto.class);
        } else {
            // 如果已经报名，则直接返回已报名的信息
            return CopyUtil.copy(memberCourseDb, MemberCourseDto.class);
        }
    }

    /**
     * 根据memberId和courseId查询记录
     */
    private MemberCourse select(String memberId, String courseId) {
        Example example = new Example(MemberCourse.class);
        example.createCriteria()
                .andEqualTo("courseId", courseId)
                .andEqualTo("memberId", memberId);
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberCourseList)) {
            return null;
        } else {
            return memberCourseList.get(0);
        }
    }

    /**
     * 获取报名信息
     *
     * @param memberCourseDto
     * @return
     */
    @Override
    public MemberCourseDto getEnroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        return CopyUtil.copy(memberCourse, MemberCourseDto.class);
    }
}