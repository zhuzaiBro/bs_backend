package com.gs.project.biz.service.impl;

import com.gs.project.biz.domain.IntegralAction;
import com.gs.project.biz.domain.User;
import com.gs.project.biz.mapper.IntegralRelMapper;
import com.gs.project.biz.mapper.UserMapper;
import com.gs.project.biz.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IntegralRelMapper integralRelMapper;

    @Override
    public void ChangeUserIntegral(IntegralAction action) {

        // 处理用户的积分
        // 获取用户
        User data = userMapper.selectUserById(action.getUserId());
        if (action.getType() == 2) {
            // 2 增加
            data.setIntegral(data.getIntegral().add(action.getNum()));
        } else {
            // 减少
            data.setIntegral(data.getIntegral().subtract(action.getNum()));
        }
        userMapper.updateUserInfo(data);
        integralRelMapper.insertRel(action);

    }

    @Override
    public void addIntegralRel(IntegralAction action) {

    }

    @Override
    public List<IntegralAction> getTodayRel(long userId) {

        return integralRelMapper.selectTodayRel(userId, 0, 10);
    }

    @Override
    public List<IntegralAction> getMyRel(long userId) {

        return integralRelMapper.selectRelList(userId, 0, 10);
    }
}
