package com.gs.project.biz.service.impl;

import com.gs.project.biz.domain.*;
import com.gs.project.biz.mapper.IntegralGoodMapper;
import com.gs.project.biz.mapper.IntegralRelMapper;
import com.gs.project.biz.mapper.UserMapper;
import com.gs.project.biz.service.IntegralGoodService;
import com.gs.project.biz.service.IntegralService;
import com.gs.project.biz.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class IntegralGoodServiceimpl implements IntegralGoodService {

    @Autowired
    IntegralGoodMapper integralGoodMapper;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    IntegralService integralService;

    @Autowired
    IntegralRelMapper integralRelMapper;

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
            IntegralAction action = new IntegralAction();
            action.setNum(goodInfo.getIntegral());
            action.setType(1);
            action.setUserId(userId);
            action.setInfo("用户兑换积分商品"+ goodInfo.getName());
            integralRelMapper.insertRel(action);

            // ticket
            Ticket t = new Ticket();
            t.setGoodId(goodInfo.getId());
            t.setUserId(userId);
            t.setName(goodInfo.getName());
            t.setNum(goodInfo.getIntegral());
            t.setCover(goodInfo.buildVo().getBanners().get(0));
            ticketService.insertTicket(t);
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

    @Override
    public IntegralGoodVo getGoodDetail(long goodId) {
        IntegralGood data = integralGoodMapper.selectGoodById(goodId);
        return data.buildVo();
    }
}
