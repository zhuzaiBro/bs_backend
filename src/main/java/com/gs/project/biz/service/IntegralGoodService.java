package com.gs.project.biz.service;

import com.gs.project.biz.domain.IntegralGood;
import com.gs.project.biz.domain.IntegralGoodVo;
import com.gs.project.biz.domain.IntegralParam;

import java.util.List;

public interface IntegralGoodService {

    // 处理积分兑换
    public void handleExchange(long userId, long goodId) throws Exception;

    // 列表查询
    public List<IntegralGoodVo> getGoodList(IntegralParam params);

    // 创建
    public void createIntegralGood(IntegralGood goodInfo);

    // 查询
    public void updateGoodInfo(IntegralGood goodInfo);

}
