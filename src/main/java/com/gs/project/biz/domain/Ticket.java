package com.gs.project.biz.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

// 兑换券
@Data
public class Ticket {
    private long id;

    private String name;

    private long goodId;

    private long userId;

    private boolean isUsed;

    private Date createAt;

    private String cover;

    private BigDecimal num;


}
