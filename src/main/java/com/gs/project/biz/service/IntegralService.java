package com.gs.project.biz.service;

import com.gs.project.biz.domain.IntegralAction;

import java.util.List;

public interface IntegralService {
    public void ChangeUserIntegral(IntegralAction action);

    public void addIntegralRel(IntegralAction action);

    public List<IntegralAction> getTodayRel(long userId);
}
