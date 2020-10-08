package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zmq
 * @date 2020/10/8 3:38 下午
 */
@RestController
@RequestMapping("admin/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @PostMapping("findAll")
    public PageDto findAll(@RequestBody PageDto pageDto){
        chapterService.findAll(pageDto);
        return pageDto;
    }
}
