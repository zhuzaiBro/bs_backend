package com.gs.project.biz.mapper;

import com.gs.project.biz.domain.IntegralGood;
import com.gs.project.biz.domain.IntegralParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface IntegralGoodMapper {
    public List<IntegralGood> getGoodList(Integer pageBegin, Integer pageSize, String order);

    public void updateGood(IntegralGood info);

    public IntegralGood selectGoodById(long id);

    public void createIntegralGood(IntegralGood goodInfo);
}
