package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zmq
 * @date 2020/10/8 3:32 下午
 */
@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    /**
     * 查询列表
     *
     * @param pageDto
     */
    public void list(PageDto<ChapterDto> pageDto) {
        Example example = new Example(Chapter.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    /**
     * 新增大章
     *
     * @param chapterDto
     */
    public void save(ChapterDto chapterDto) {
        chapterDto.setId(UuidUtil.getShortUuid());
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        chapterMapper.insert(chapter);
    }
}
