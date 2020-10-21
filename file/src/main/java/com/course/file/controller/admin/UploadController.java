package com.course.file.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.utils.UuidUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author zmq
 * @date 2020/10/20 9:41 下午
 */
@RestController
@RequestMapping("admin")
public class UploadController {

    @PostMapping("upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath = "/Users/zengmingqi/Pictures/"+key+"-"+filename;
        File dest = new File(fullPath);
        file.transferTo(dest);
        String path = "http://127.0.0.1:9000/file/f/"+key+"-"+filename;
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(),null,path);
    }
}
