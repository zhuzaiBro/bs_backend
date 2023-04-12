package com.gs.project.biz.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gs.common.utils.file.TCCOSConfig;
import com.gs.common.utils.file.URLRecognition;
import com.gs.framework.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Api("识别API")
@RestController
@RequestMapping("/voice")
// 语音识别
public class RecognizeVoiceController {

    @Autowired
    private TCCOSConfig conf;

    @Autowired
    private URLRecognition ur;

    private static final Logger logger = LoggerFactory.getLogger(RecognizeVoiceController.class);

    @PostMapping("/recognize")
    public AjaxResult RecognizeVoice(@RequestPart("file") MultipartFile file) {

        String identificationResults = null;
        logger.info("开始处理文件。");
        if (file == null) {
            logger.info("上传的文件不存在。");
            return new AjaxResult(500, "上传的文件不存在");
        }
        String URL = null;
        logger.info("开始文件上传至腾讯云COS。");

        try {
            URL = conf.upLoadFile2COS(file.getSize(), Objects.requireNonNull(file.getOriginalFilename()), file, "voice");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("开始通过URL进行语音识别。");
        try {
            identificationResults = ur.getIdentificationResults(URL);
            JSONObject jsonObject = JSONObject.parseObject(identificationResults);
//            System.out.println(jsonObject.getString("Result"));
//            //得到JsonString中的用户话语。
////            String word = jsonObject.getString("data");
//            JSONObject data = (JSONObject) jsonObject.get("data");
//            JSONObject D =  (JSONObject) data.get("Data");
//            String word = D.getString("Result");
//            System.out.println(data.get("TaskId"));
            return new AjaxResult(200, "", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("Result", "电池");
        return new AjaxResult(200, "", map);
    }


    @PostMapping("/type")
    public AjaxResult OcrRecognize(@RequestPart("file") MultipartFile file) throws IOException {
//        logger.info("开始处理文件。");
//        if (file == null) {
//            logger.info("上传的文件不存在。");
//            return new AjaxResult(500, "上传的文件不存在");
//        }
//        String imgUrl = null;
//        logger.info("开始文件上传至腾讯云COS。");
//
//        try {
//            imgUrl = conf.upLoadFile2COS(file.getSize(), Objects.requireNonNull(file.getOriginalFilename()), file, "voice");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        byte[] bytes = file.getBytes();
        String img = encodeBase64String(bytes);
        String tianapi_data = "";
        try {

            URL url = new URL( "https://apis.tianapi.com/imglajifenlei/index");
            HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStream outputStream = conn.getOutputStream();
            String content = "key=4101b92b0dd1d1264d84e62f299a51ce&img=" + img;
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = conn.getInputStream();
            byte[] data = new byte[1024];
            StringBuilder tianapi = new StringBuilder();
            while (inputStream.read(data) != -1) {
                String t = new String(data);
                tianapi.append(t);
            }
            tianapi_data = tianapi.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        JSONObject data = JSON.parseObject(tianapi_data);
//        System.out.println(tianapi_data);
        return new AjaxResult(200, "", tianapi_data);

    }
}
