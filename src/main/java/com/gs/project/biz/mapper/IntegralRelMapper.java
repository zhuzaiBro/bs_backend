package com.gs.project.biz.mapper;


import com.gs.project.biz.domain.IntegralAction;
import com.gs.project.biz.domain.IntegralGood;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IntegralRelMapper {
    public void insertRel(IntegralAction action);

    public IntegralAction getRelById(long id);

    public List<IntegralAction> getRelListByUserId(long userId, Integer begin, Integer end);

    public void updateIntegralRel(IntegralAction action);

    // 查询用户当天积分变动
    public List<IntegralAction> selectTodayRel(long userId, Integer begin, Integer end);
}
