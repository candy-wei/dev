package com.ningyuan.mobile.dto;

import com.ningyuan.mobile.model.ShopGoodsModel;
import com.ningyuan.mobile.model.ShopGoodsSkuModel;

import java.math.BigDecimal;

public class ShopCartDto {
    private Long id;
    private ShopGoodsModel goods;
    private ShopGoodsSkuModel skuModel;
    private Long idGoods;
    private Long idSku;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal fare;

    public BigDecimal getPrice() {
        if (idSku != null) {
            return skuModel.getPrice();
        }
        return goods.getPrice();
    }

    public BigDecimal getFare() {
        return goods.getFare();
    }

    public String getTitle() {
        return idSku != null ? getGoods().getName() + " " + getSkuModel().getCodeName() : getGoods().getName();
    }

    public Long getIdGoods() {
        return idGoods;
    }

    public void setIdGoods(Long idGoods) {
        this.idGoods = idGoods;
    }

    public ShopGoodsModel getGoods() {
        return goods;
    }

    public void setGoods(ShopGoodsModel goods) {
        this.goods = goods;
    }

    public Long getIdSku() {
        return idSku;
    }

    public void setIdSku(Long idSku) {
        this.idSku = idSku;
    }

    public ShopGoodsSkuModel getSkuModel() {
        return skuModel;
    }

    public void setSkuModel(ShopGoodsSkuModel skuModel) {
        this.skuModel = skuModel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
