package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
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
    public ResponseDto list(@RequestBody PageDto<ChapterDto> pageDto){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        chapterService.list(pageDto);
        return responseDto;
    }

    @PostMapping("save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        chapterService.save(chapterDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    @DeleteMapping("delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        chapterService.delete(id);
        return new ResponseDto();
    }
}
