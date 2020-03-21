package com.ningyuan.mobile.dto;

public class CartAddDto {
    private Long idGoods;
    private Integer count;
    private Long idSku;
    private String openId;

    public Long getIdGoods() { return idGoods; }

    public void setIdGoods(Long idGoods) { this.idGoods = idGoods; }

    public Integer getCount() { return count; }

    public void setCount(Integer count) { this.count = count; }

    public Long getIdSku() { return idSku; }

    public void setIdSku(Long idSku) { this.idSku = idSku; }

    public String getOpenId() { return openId; }

    public void setOpenId(String openId) { this.openId = openId; }
}
