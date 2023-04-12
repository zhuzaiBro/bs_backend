package com.gs.project.biz.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.gs.common.constant.Constants;
import com.gs.common.utils.JsonUtils;
import com.gs.common.utils.SecurityUtils;
import com.gs.common.utils.wechat.WxMaProperties;
import com.gs.framework.security.LoginUser;
import com.gs.framework.security.service.TokenService;
import com.gs.framework.web.domain.AjaxResult;
import com.gs.project.biz.domain.User;
import com.gs.project.biz.domain.WxLoginRequest;
import com.gs.project.biz.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Api("普通用户登录")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/user/")
public class WxMaUserController {
    private final WxMaService wxMaService;

    @Autowired
    WxMaProperties weConf;

    @Autowired
    UserService userServ;

    @Autowired
    private TokenService tokenService;

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody WxLoginRequest req) {
        if (StringUtils.isBlank(req.code)) {
            return new AjaxResult(500, "empty jscode");
        }
        String appid = weConf.getConfigs().get(0).getAppid();
        if (!wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        try {
            AjaxResult ajax = AjaxResult.success();
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(req.code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            //TODO 自己的业务逻辑

            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), req.encryptedData, req.iv);
            userInfo.setOpenId(session.getOpenid());
            WxMaConfigHolder.remove();//清理ThreadLocal

            User u = User.buildUserFromWxMaUserInfo(userInfo);

            User data = userServ.handleUserLogin(u);

            LoginUser usr = new LoginUser(data);
            String token = tokenService.createToken(usr);

            ajax.put(Constants.TOKEN, Constants.TOKEN_PREFIX + token);

            return ajax;

        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return new AjaxResult(500, e.toString());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    /**
     * 管理后台获取用户列表
     *
     */
    @GetMapping("/")
    public AjaxResult getInfo(Integer page, Integer size, String order) {

        AjaxResult ajax = AjaxResult.success();

        List<User> list = userServ.getUserList(page, size, order);

        ajax.put("data", list);
        return ajax;
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        String appid = weConf.getConfigs().get(0).getAppid();
        if (!wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {

        String appid = weConf.getConfigs().get(0).getAppid();
        if (!wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtils.toJson(phoneNoInfo);
    }

    /***
     * 用户获取自己的信息
     */
    @GetMapping("/userInfo")
    public AjaxResult getUserInfo() {

        try {
            LoginUser user = SecurityUtils.getLoginUser();
            return new AjaxResult(200, "", userServ.getUserInfo(user.getUser().getId()));

        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }


    }


}
