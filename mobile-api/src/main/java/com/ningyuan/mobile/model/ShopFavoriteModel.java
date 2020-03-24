package com.ningyuan.mobile.model;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ningyuan.base.BaseModel;
import com.ningyuan.base.annotation.FiledComment;

import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Tue Mar 24 16:55:30 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="shop_favorite" )
public class ShopFavoriteModel extends BaseModel {

    @Column(name = "open_id" )
    private String openId;

    /**
     * 商品id
     */
    @FiledComment(text = "商品id" )
    @Column(name = "id_goods" )
    private Long idGoods;

    @Column(name = "create_time" )
    private Date createTime;

    @Column(name = "update_time" )
    private Date updateTime;

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getIdGoods() {
        return this.idGoods;
    }

    public void setIdGoods(Long idGoods) {
        this.idGoods = idGoods;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}