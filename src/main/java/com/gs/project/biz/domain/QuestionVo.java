package com.gs.project.biz.domain;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVo {

    // 题型
    private String type;

    // 图片
    private String picUrl;

    // 答案
    private String ans;

    // 题干
    private String title;

    // 用户回答
    private String uAnswer;

    // 选项
    private List<String> options;


}
