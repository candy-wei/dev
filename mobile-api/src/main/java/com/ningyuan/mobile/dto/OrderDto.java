package com.ningyuan.mobile.dto;

import com.ningyuan.mobile.model.ShopAddressModel;

import java.util.List;

public class OrderDto {
    private Long id;
    private Long idAddress;
    private String openId;
    private String orderSn;
    private String realPrice;
    private String status;
    private String statusName;
    private String totalPrice;
    private String createTime;
    private ShopAddressModel address;
    private List<OrderGoodsDto> goods;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getIdAddress() { return idAddress; }

    public void setIdAddress(Long idAddress) { this.idAddress = idAddress; }

    public String getOpenId() { return openId; }

    public void setOpenId(String openId) { this.openId = openId; }

    public String getOrderSn() { return orderSn; }

    public void setOrderSn(String orderSn) { this.orderSn = orderSn; }

    public String getRealPrice() { return realPrice; }

    public void setRealPrice(String realPrice) { this.realPrice = realPrice; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getStatusName() { return statusName; }

    public void setStatusName(String statusName) { this.statusName = statusName; }

    public String getTotalPrice() { return totalPrice; }

    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public ShopAddressModel getAddress() { return address; }

    public void setAddress(ShopAddressModel address) { this.address = address; }

    public List<OrderGoodsDto> getGoods() { return goods; }

    public void setGoods(List<OrderGoodsDto> goods) { this.goods = goods; }
}
