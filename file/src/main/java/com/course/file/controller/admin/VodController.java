package com.course.file.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.FileUseEnum;
import com.course.server.service.IFileService;
import com.course.server.utils.Base64ToMultipartFile;
import com.course.server.utils.VodUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class VodController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Resource
    private IFileService fileService;

    @PostMapping("/vod")
    public ResponseDto fileUpload(@RequestBody FileDto fileDto) throws Exception {
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        String dir = useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;
        String vod = "";
        String fileUrl = "";
        // 初始化VOD客户端并获取上传地址和凭证
        DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
        CreateUploadVideoResponse createUploadVideoResponse = VodUtil.createUploadVideo(vodClient, path);
        // 执行成功会返回VideoId、UploadAddress和UploadAuth
        vod = createUploadVideoResponse.getVideoId();
        JSONObject uploadAuth = JSONObject.parseObject(
                Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()), JSONObject.class);
        JSONObject uploadAddress = JSONObject.parseObject(
                Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()), JSONObject.class);
        // 使用UploadAuth和UploadAddress初始化OSS客户端
        OSSClient ossClient = VodUtil.initOssClient(uploadAuth, uploadAddress);
        // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
        VodUtil.uploadLocalFile(ossClient, uploadAddress, shard.getInputStream());
        GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, vod);
        fileUrl = response.getMezzanine().getFileURL();

        // 关闭OSSClient。
        ossClient.shutdown();
        fileDto.setPath(path);
        fileDto.setVod(vod);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(fileUrl);
        responseDto.setContent(fileDto);
        responseDto.setSuccess(true);

//        if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
//            this.merge(fileDto);
//        }
        return responseDto;
    }

    @GetMapping(value = "/get-auth/{vod}")
    public ResponseDto getAuth(@PathVariable String vod) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = VodUtil.getVideoPlayAuth(client, vod);
        responseDto.setContent(response.getPlayAuth());
        return responseDto;
    }
}
