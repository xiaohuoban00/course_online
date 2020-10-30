package com.course.file.controller.admin;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.enmus.FileUseEnum;
import com.course.server.service.IFileService;
import com.course.server.utils.Base64ToMultipartFile;
import com.course.server.utils.UuidUtil;
import com.course.server.utils.VodUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    @Value("${oss.domain}")
    private String ossDomain;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Resource
    private IFileService fileService;

    @PostMapping("upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);
        String suffix = fileDto.getSuffix();
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdirs();
        }
        //存储到数据库的path
        String path = dir + File.separator + key + "." + suffix;
        String localPath = path + "." + fileDto.getShardIndex();
        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LocalDateTime now = LocalDateTime.now();
        fileDto.setPath(path);
        fileDto.setUse(useEnum.getCode());
        fileDto.setCreatedAt(now);
        fileDto.setUpdatedAt(now);
        fileService.save(fileDto);
        fileDto.setPath(FILE_DOMAIN + path);
        if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
            this.merge(fileDto);
        }
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, fileDto);
    }


    private void merge(FileDto fileDto) throws Exception {
        String path = fileDto.getPath();
        path = path.replace(FILE_DOMAIN, "");
        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        FileInputStream fileInputStream = null;
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            for (int i = 1; i <= shardTotal; i++) {
                fileInputStream = new FileInputStream(new File(FILE_PATH + path + "." + i));
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            outputStream.close();
        }
        System.gc();
        //删除分片
        for (int i = 1; i <= shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + i;
            File file = new File(filePath);
            file.delete();
        }
    }

    @GetMapping("check/{key}")
    public ResponseDto check(@PathVariable String key) throws Exception{
        FileDto fileDto = fileService.findByKey(key);
        if (fileDto != null) {
            if(StringUtils.isEmpty(fileDto.getVod())){
                fileDto.setPath(ossDomain+fileDto.getPath());
            }else {
                DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
                GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, fileDto.getVod());
                String fileUrl = response.getMezzanine().getFileURL();
                fileDto.setPath(fileUrl);
            }
        }
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null, fileDto);
    }
}
