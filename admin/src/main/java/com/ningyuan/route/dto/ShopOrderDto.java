package com.ningyuan.route.dto;

public class ShopOrderDto {
    private Long id;
    private String nickname;
    private String receiver;
    private String message;
    private String orderSn;
    private String realPrice;
    private String status;
    private String totalPrice;
    private String hasPay;
    private String createTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getOrderSn() { return orderSn; }

    public void setOrderSn(String orderSn) { this.orderSn = orderSn; }

    public String getRealPrice() { return realPrice; }

    public void setRealPrice(String realPrice) { this.realPrice = realPrice; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTotalPrice() { return totalPrice; }

    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }

    public String getHasPay() { return hasPay; }

    public void setHasPay(String hasPay) { this.hasPay = hasPay; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }
}
