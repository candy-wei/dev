package com.ningyuan.mobile.dto;

import com.ningyuan.mobile.model.ShopCustomerModel;
import com.ningyuan.mobile.model.ShopGoodsModel;
import com.ningyuan.mobile.model.ShopGoodsSkuModel;
import java.math.BigDecimal;

public class ShopCartDto {
    private Long id;
    private ShopCustomerModel customerModel;
    private ShopGoodsModel goods;
    private ShopGoodsSkuModel sku;
    private Long idGoods;
    private Long idSku;
    private BigDecimal amount;
    private BigDecimal price;
    private String title;

    public BigDecimal getPrice(){
        if(idSku!=null){
            return sku.getPrice();
        }
        return goods.getPrice();
    }
    public String getTitle(){
        return idSku!=null? getGoods().getName()+" "+getSku().getCodeName():getGoods().getName();
    }
    public ShopCustomerModel getCustomerModel() { return customerModel; }

    public void setCustomerModel(ShopCustomerModel customerModel) { this.customerModel = customerModel; }

    public Long getIdGoods() { return idGoods; }

    public void setIdGoods(Long idGoods) { this.idGoods = idGoods; }

    public ShopGoodsModel getGoods() { return goods; }

    public void setGoods(ShopGoodsModel goods) { this.goods = goods; }

    public Long getIdSku() { return idSku; }

    public void setIdSku(Long idSku) { this.idSku = idSku; }

    public ShopGoodsSkuModel getSku() { return sku; }

    public void setSku(ShopGoodsSkuModel sku) { this.sku = sku; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }
}
