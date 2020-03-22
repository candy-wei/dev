package com.ningyuan.mobile.model;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ningyuan.base.BaseModel;
import com.ningyuan.base.annotation.FiledComment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Sat Mar 21 14:55:34 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="shop_cart" )
public class ShopCartModel extends BaseModel {

    /**
     * 数量
     */
    @FiledComment(text = "数量" )
    @Column(name = "count" )
    private BigDecimal count;

    /**
     * 商品id
     */
    @FiledComment(text = "商品id" )
    @Column(name = "id_goods" )
    private Long idGoods;

    /**
     * skuId
     */
    @FiledComment(text = "skuId" )
    @Column(name = "id_sku" )
    private Long idSku;

    @Column(name = "open_id" )
    private String openId;

    public BigDecimal getCount() { return count; }

    public void setCount(BigDecimal count) { this.count = count; }

    public Long getIdGoods() {
        return this.idGoods;
    }

    public void setIdGoods(Long idGoods) {
        this.idGoods = idGoods;
    }

    public Long getIdSku() {
        return this.idSku;
    }

    public void setIdSku(Long idSku) {
        this.idSku = idSku;
    }

    public String getOpenId() { return openId; }

    public void setOpenId(String openId) { this.openId = openId; }

}
