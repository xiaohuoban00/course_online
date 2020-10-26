package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.IFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("admin/file")
public class FileController {

    @Resource
    private IFileService fileService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<FileDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        fileService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param fileDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody FileDto fileDto) {
        fileService.save(fileDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(fileDto);
        return responseDto;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        fileService.delete(id);
        return new ResponseDto();
    }
}