package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.enmus.FileUseEnum;
import com.course.server.service.IFileService;
import com.course.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author zmq
 * @date 2020/10/20 9:41 下午
 */
@RestController
@RequestMapping("admin")
public class UploadController {

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Resource
    private IFileService fileService;

    @PostMapping("upload")
    public ResponseDto upload(@RequestParam MultipartFile file, String use) throws IOException {
        String filename = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        String dir = useEnum.name().toLowerCase();
        String path = dir + File.separator + key + "." + suffix;
        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(dest);
        LocalDateTime now = LocalDateTime.now();
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(filename);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse(useEnum.getCode());
        fileDto.setCreatedAt(now);
        fileDto.setUpdatedAt(now);
        fileService.save(fileDto);
        fileDto.setPath(FILE_DOMAIN + path);
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, fileDto);
    }
}
