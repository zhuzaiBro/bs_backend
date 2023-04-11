package com.gs.project.biz.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 积分兑换商品
 */
public class IntegralGood {
    private long id;

    private String name;

    private String description;



    private String content;

    private Integer stock;

    // 兑换所需要的积分
    private BigDecimal integral;

    private String banners;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public IntegralGoodVo buildVo() {
        IntegralGoodVo vo = new IntegralGoodVo();
        vo.setName(name);
        vo.setIntegral(integral);
        vo.setDescription(description);
        vo.setStock(stock);
        vo.setId(id);
        vo.setContent(content);


        String str = banners.substring(1, banners.length() - 1);

//        String[] bannerData = str.split(",");

        List<String> bannerData= JSONArray.parseArray(banners, String.class);
//        bannerData = JSONObject.parseArray(banners, String);
        vo.setBanners(bannerData);
        return vo;
    }
}
