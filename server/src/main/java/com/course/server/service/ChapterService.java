package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    public List<ChapterDto> findAll() {
        List<Chapter> chapterList = chapterMapper.selectAll();
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        ChapterDto chapterDto;
        for (Chapter chapter : chapterList) {
            chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        return chapterDtoList;
    }
}
