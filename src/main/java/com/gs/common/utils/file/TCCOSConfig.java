package com.gs.common.utils.file;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Configuration
@Data
public class TCCOSConfig {


    private static final Logger logger = LoggerFactory.getLogger(TCCOSConfig.class);

    // 读取 config.yml 文件中的配置信息
    @Value("${cos.SecretId}")
    private String secretId;
    @Value("${cos.SecretKey}")
    private String secretKey;

    @Value("${cos.region}")
    private String region;

    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.url}")
    private String url;

    @Value("${cos.path}")
    private String path;

    @Value("${cos.policy_expire}")
    public Integer policyExpire;

    @Value("${cos.code_format}")
    public String codeFormat;


    @Bean
    // 创建 COSClient 实例
    public COSClient cosClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(this.secretId, this.secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }


    public String upLoadFile2COS(Long fileSize, String filename, MultipartFile file, String filepath) throws IOException {


        // 创建cos客户端。
        COSClient cosClient = this.cosClient();
        // 获取输入流
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ObjectMetadata objectMetadata = new ObjectMetadata();

        // 设置输入流长度为500。
        // 这里要强调一下，因为腾讯云支持本地文件上传和文件流上传，
        // 为了不必要的麻烦所以选择文件流上传，根据官方文档，为了
        // 避免oom，必须要设置元数据并告知输入流长度。
        objectMetadata.setContentLength(fileSize);

        //上传对象，命名采用UUID防止文件名重复。
        String eName = filename.substring(filename.lastIndexOf("."));
        String key = UUID.randomUUID() + eName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // 通过回调函数判断是否上传成功，有etag信息则表示上传成功，否则上传失败。
        if (putObjectResult.getETag() != null){
            logger.info("文件上传成功。");
        }else{
            logger.info("文件上传失败。");
            return null;
        }
        logger.info("开始生成URL。");
        //生成访问对象的URL的String形式。
        String url = this.getUrl() + key;
        System.out.println(url);
        // 完成上传之后，关闭连接。
        destory(cosClient);
        return url;

    }


    // 关闭连接。
    public static void destory(COSClient cosClient) {
        cosClient.shutdown();
    }
}