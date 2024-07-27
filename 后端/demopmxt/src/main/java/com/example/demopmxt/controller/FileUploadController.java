package com.example.demopmxt.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
public class FileUploadController {

    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "LTAI5tExxK3kGwJA3dGYkVMg";
    private String accessKeySecret = "VBjhqAxMvey4nF7eZL1x1B7dVx6xMY";
    private String bucketName = "web-pmxt";

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("image") MultipartFile file) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建时间戳
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());
            // 创建UUID
            String uuid = UUID.randomUUID().toString();
            // 组合文件名
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = uuid + "_" + timestamp + extension;
            // 替换所有非法字符
            fileName = fileName.replace(":", "-").replace(" ", "_");
            // 上传文件
            ossClient.putObject(bucketName, fileName, file.getInputStream());
            // 生成URL
            Date expiration = new Date(System.currentTimeMillis() + 7*24*3600* 1000); // 1小时后过期
            URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            log.info("Uploaded file URL: " + url.toString());
            return url.toString();
        } catch (Exception e) {
            log.error("Upload failed", e);
            return "Upload failed: " + e.getMessage();
        } finally {
            ossClient.shutdown();
        }
    }
}
