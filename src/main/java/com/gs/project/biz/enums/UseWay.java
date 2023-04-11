package com.gs.project.biz.enums;


import lombok.Getter;

public enum UseWay {
    BUY_GOODS(0, "兑换商品"),
    ANSWER_QUESTION(1, "答题获奖");


    @Getter
    private final Integer value;
    @Getter private final String des;

    UseWay(Integer value, String des) {

        this.value = value;
        this.des = des;
    }
}
