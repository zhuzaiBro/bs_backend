package com.gs.common.utils.file;

import lombok.Data;

/**
 * 返回结果集
 *
 */
@Data
public class PicUploadResult {
    // 文件惟一标识
    private String uid;

    // 文件名
    private String name;

    // 状态有：uploading done error removed
    private String status;

    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}

