package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("findAll")
    public List<ChapterDto> findAll(){
        return chapterService.findAll();
    }
}
