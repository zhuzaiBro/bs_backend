package com.gs.project.biz.service.impl;

import com.gs.project.biz.domain.IntegralGood;
import com.gs.project.biz.domain.IntegralGoodVo;
import com.gs.project.biz.domain.IntegralParam;
import com.gs.project.biz.domain.User;
import com.gs.project.biz.mapper.IntegralGoodMapper;
import com.gs.project.biz.mapper.UserMapper;
import com.gs.project.biz.service.IntegralGoodService;
import com.gs.project.biz.service.IntegralService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntegralGoodServiceimpl implements IntegralGoodService {

    @Autowired
    IntegralGoodMapper integralGoodMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    IntegralService integralService;

    @Override
    public void handleExchange(long userId, long goodId) throws Exception {

        User u = userMapper.selectUserById(userId);
        IntegralGood goodInfo = integralGoodMapper.selectGoodById(goodId);
        // 判断是否可以兑换
        System.out.println(u.getIntegral()+ "   用户积分" );
        if (u.getIntegral().compareTo(goodInfo.getIntegral()) > 0 ) {
            // 积分足够
            u.setIntegral(u.getIntegral().subtract(goodInfo.getIntegral()));
            userMapper.updateUserInfo(u);
            // v1版本默认先每次兑换一个
            goodInfo.setStock(goodInfo.getStock() - 1);
            integralGoodMapper.updateGood(goodInfo);
        } else {
            throw new Exception("用户积分不足！");
        }
    }

    @Override
    public List<IntegralGoodVo> getGoodList(IntegralParam params) {
        List<IntegralGoodVo> result = new ArrayList();
        Integer pageBegin = (params.getPage() - 1) * params.getSize();
        List<IntegralGood> list = integralGoodMapper.getGoodList(pageBegin, params.getSize(), params.getOrder());

        list.forEach(it -> {
            result.add(it.buildVo());
        });
        return result;
    }

    @Override
    public void createIntegralGood(IntegralGood goodInfo) {
        System.out.println(goodInfo.toString());
        integralGoodMapper.createIntegralGood(goodInfo);
    }

    @Override
    public void updateGoodInfo(IntegralGood goodInfo) {
        integralGoodMapper.updateGood(goodInfo);
    }

    @Override
    public void deleteGoods(long[] ids) {
        for(long id : ids ) {
            // 遍历逐个进行删除
            integralGoodMapper.deleteIntegralGood(id);
        }
    }
}
