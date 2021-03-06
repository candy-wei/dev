package com.ningyuan.mobile.model;

import javax.persistence.Column;
import com.ningyuan.base.BaseModel;
import com.ningyuan.base.annotation.FiledComment;

import javax.persistence.Table;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Fri Apr 03 14:41:01 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="shop_wallet" )
public class ShopWalletModel extends BaseModel {

    @Column(name = "open_id" )
    private String openId;

    /**
     * 账户金额
     */
    @FiledComment(text = "账户金额" )
    @Column(name = "finance" )
    private String finance;

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

    public String getFinance() {
        return this.finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
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
