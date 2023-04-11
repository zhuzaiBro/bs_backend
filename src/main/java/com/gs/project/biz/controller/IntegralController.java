package com.gs.project.biz.controller;

import com.gs.common.utils.SecurityUtils;
import com.gs.framework.web.domain.AjaxResult;
import com.gs.project.biz.domain.*;
import com.gs.project.biz.service.IntegralGoodService;
import com.gs.project.biz.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/integral")
public class IntegralController {

    @Autowired
    IntegralGoodService integralGoodService;

    @Autowired
    IntegralService integralService;

    // 获取积分兑换列表
    @GetMapping("/goods")
    public AjaxResult getIntegralGoods(IntegralParam params) {

         List<IntegralGoodVo> goods = integralGoodService.getGoodList(params);

        return new AjaxResult(200 , "", goods);
    }

    // 兑换
    @PostMapping("/exchange")
    public AjaxResult exchangeGood(@RequestBody ExchangeParam params) throws Exception {

        long userId = SecurityUtils.getLoginUser().getUser().getId();
        integralGoodService.handleExchange(userId, params.getGoodId());


        return new AjaxResult(200, "", "兑换成功");
    }

    // 上传商品
    @PostMapping("/")
    public AjaxResult uploadGood(@RequestBody IntegralGoodVo goodInfo) {

        integralGoodService.createIntegralGood(goodInfo.buildIntegralGood());

        return new AjaxResult(200, "上传成功", "上传成功");
    }


    // 修改商品
    @PutMapping("/")
    public AjaxResult updateGood(@RequestBody  IntegralGoodVo goodInfo) {
        integralGoodService.updateGoodInfo(goodInfo.buildIntegralGood());

        return AjaxResult.success();
    }

    // 查询当天变动记录
    @PostMapping("/rel")
    public AjaxResult getTodayRel() {
       AjaxResult ajax = AjaxResult.success();

       long userId = SecurityUtils.getLoginUser().getUser().getId();
       List<IntegralAction> list = integralService.getTodayRel(userId);

       ajax.put("data", list);
       return ajax;
    }
}
