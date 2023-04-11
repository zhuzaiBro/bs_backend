package com.gs.project.system.controller;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectResult;
import com.gs.common.utils.file.TCCOSConfig;
import com.gs.framework.web.controller.BaseController;
import com.gs.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
public class UploadController extends BaseController {

    @Autowired
    private TCCOSConfig conf;


    @PostMapping("/api/upload")
    public AjaxResult UpdateFile(@RequestPart("file") MultipartFile file) {
        String oldFileName = file.getOriginalFilename();
        assert oldFileName != null;
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + eName;


        COSClient client = null;
        File localFile;
        PutObjectResult putObjectResult = null;
        String key;
        try {
            client = conf.cosClient();
            localFile = File.createTempFile("temp", ".png");
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            key = "upload/" + newFileName;
            client.generatePresignedUrl(conf.getBucketName(), key, new Date());

            putObjectResult = client.putObject(conf.getBucketName(), key, localFile);

        } catch (IOException e) {
            return new AjaxResult(500, "", e);
        } finally {
            // 关闭客户端(关闭后台线程)
            assert client != null;
            client.shutdown();
        }
        return new AjaxResult(200, "上传成功", conf.getUrl() + key);
    }


}
