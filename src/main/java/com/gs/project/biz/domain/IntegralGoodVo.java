package com.gs.project.biz.domain;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class IntegralGoodVo {
    private long id;

    private String name;

    private String description;



    private String content;

    private Integer stock;

    // 兑换所需要的积分
    private BigDecimal integral;

    private List<String> banners;

    public IntegralGood buildIntegralGood() {
        IntegralGood data = new IntegralGood();
        data.setStock(stock);
        data.setIntegral(integral);
        data.setDescription(description);
        data.setName(name);
        data.setContent(content);
        data.setId(id);
        String bannerStr = JSON.toJSONString(banners);
        data.setBanners(bannerStr);

        return data;
    }
}
