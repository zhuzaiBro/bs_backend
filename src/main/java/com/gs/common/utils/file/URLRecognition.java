package com.gs.common.utils.file;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.asr.v20190614.models.*;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URLRecognition {
    @Value("${cos.SecretId}")
    private String secretId;
//            = "AKIDNaYHl13ua2E9QUJr89wfLPM6eIqAe5Sy";
    @Value("${cos.SecretKey}")
    private String secretKey;
//    = "b4LTRGsEgx0ob5OSLPIwyVouncN0Tkqp";

    public String getIdentificationResults(String url) {


        String identificationResults=null;
        // 采用语音URL方式调用（一句话识别）。
        try {

//            System.out.println("密钥：" + this.secretId +  "key：" + this.secretKey );
            Credential cred = new Credential(secretId, secretKey);



            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("asr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            AsrClient client = new AsrClient(cred, "ap-nanjing", clientProfile);
            //URL在参数中。（"Url":"https://ruskin-1256085166.cos.ap-shanghai.myqcloud.com/test.wav"）
            String params = "{\"ProjectId\":0,\"SubServiceType\":2,\"EngSerViceType\":\"16k_zh\",\"Source" +
                    "Type\":0,\"Url\":\"" + url + "\",\"VoiceFormat\":\"mp3\",\"UsrAudioKey\":\"session-123\"}";
            SentenceRecognitionRequest req = SentenceRecognitionRequest.fromJsonString(params, SentenceRecognitionRequest.class);

            SentenceRecognitionResponse resp = client.SentenceRecognition(req);

            identificationResults = SentenceRecognitionRequest.toJsonString(resp);


        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return identificationResults;
    }
}
