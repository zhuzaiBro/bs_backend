package com.gs.project.biz.domain;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class User {

    // 主键id
    private long id;

    // 用户的 开放 id
    private String maOpenId;

    // 用户昵称
    private String nickName;


    // 用户头像
    private String avatarUrl;

    // 用户手机号
    private String mobile;

    // 用户积分
    private BigDecimal integral;

    // 给一个微信小程序的信息，构建一个用户信息
    public static User buildUserFromWxMaUserInfo(WxMaUserInfo data) {

        User u = new User();

        u.maOpenId = data.getOpenId();
        u.integral= BigDecimal.valueOf(0.00);
        u.avatarUrl = data.getAvatarUrl();
        u.nickName = data.getNickName();
        u.integral = BigDecimal.valueOf(0.00);
        u.mobile = "";

        return u;
    }
}
