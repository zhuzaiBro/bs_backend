package com.gs.project.biz.domain;

import com.gs.project.biz.enums.UseWay;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class IntegralAction {
    private long id;
    private int type;
    private BigDecimal num;
    private String info;
    private long userId;
    private Date createTime;

    // 枚举积分用途 value < 0 为 减少，
    private UseWay useWay;


}
