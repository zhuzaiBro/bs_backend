package com.gs.project.biz.domain;


import lombok.Data;

@Data
public class IntegralParam {
    // 查询页号
    private Integer page;

    // 查询数量
    private Integer size;
    // 查询指令
    private String order;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
