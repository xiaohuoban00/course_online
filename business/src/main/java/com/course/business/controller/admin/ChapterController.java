package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author zmq
 * @date 2020/10/8 3:38 下午
 */
@RestController
@RequestMapping("admin/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @PostMapping("list")
    public PageDto<ChapterDto> list(@RequestBody PageDto<ChapterDto> pageDto){
        chapterService.list(pageDto);
        return pageDto;
    }

    @PostMapping("save")
    public ChapterDto save(@RequestBody ChapterDto chapterDto){
        chapterService.save(chapterDto);
        return chapterDto;
    }
}
