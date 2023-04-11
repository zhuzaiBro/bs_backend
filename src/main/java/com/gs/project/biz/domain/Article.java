package com.gs.project.biz.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    // 标题
    private String title;

    private String description;

    private String picUrl;

    private String source;


    private String url;
    // 富文本
    private String content;

    //    创作日期
    private Date createAt;


    private long id;

    // 作者信息 json
    private String author;

    // 文章出处
    private String from;

    // 访问量
    private Integer visit;


}
