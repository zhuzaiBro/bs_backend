package com.gs.project.biz.domain;

import lombok.Data;

@Data
public class WxLoginRequest {
    public String code;

    public String iv;

    public String encryptedData;
}
