package com.course.server.service.impl;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.service.IChapterService;
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

/**
 * @author zmq
 * @date 2020/10/8 3:32 下午
 */
@Service
public class ChapterServiceImpl implements IChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    @Override
    public void list(ChapterPageDto pageDto) {
        Example example = new Example(Chapter.class);
        example.orderBy("id").desc();
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(pageDto.getCourseId())){
            criteria.andEqualTo("courseId",pageDto.getCourseId());
        }
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }


    @Override
    @Transactional
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())) {
            insert(chapter);
        } else {
            update(chapter);
        }
    }

    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insertSelective(chapter);
    }

    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKeySelective(chapter);
    }


    @Override
    @Transactional
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ChapterDto> listByCourse(String courseId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        return CopyUtil.copyList(chapterList,ChapterDto.class);
    }
}
