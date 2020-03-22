package com.ningyuan.mobile.model;

import com.ningyuan.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Sat Mar 21 16:52:19 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="shop_customer" )
public class ShopCustomerModel extends BaseModel {

    @Column(name = "mobile" )
    private String mobile;

    @Column(name = "open_id" )
    private String openId;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
