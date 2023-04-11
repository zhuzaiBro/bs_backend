package com.gs.project.biz.domain;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Question {
    private long id;

    // 题目类型 select、judge
    private String type;

    // 存储题干
    /**
     *  例如：
     *  城市生活垃圾从哪里来? - A.人类活动,B.垃圾桶,C.街上,D.超市 - A
     *  西红柿是有害垃圾 - A.正确, B.错误 - F
     * */
    private String content;

    private String picUrl;


    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


    /**
     *  例如：
     *  城市生活垃圾从哪里来? - A.人类活动,B.垃圾桶,C.街上,D.超市 - A
     *  西红柿是有害垃圾 - A.正确, B.错误 - F
     * */
    public QuestionVo buildVo() {
        QuestionVo vo = new QuestionVo();
        vo.setPicUrl(picUrl);
        vo.setType(type);

        // 解析content
        String[] data = content.split("-");

        // 问题
        vo.setTitle(data[0]);
        vo.setAns(data[2]);
        String[] options = data[1].split(",");
        vo.setOptions(Arrays.asList(options));
        return vo;
    }
}
