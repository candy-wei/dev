package com.ningyuan.mobile.model;

import com.ningyuan.base.BaseModel;
import com.ningyuan.base.annotation.FiledComment;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Fri Apr 03 11:08:59 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="shop_customer" )
public class ShopCustomerModel extends BaseModel {

    @Column(name = "mobile" )
    private String mobile;

    @Column(name = "open_id" )
    private String openId;

    /**
     * 会员等级,0:不是,1:普通会员,依次类推
     */
    @FiledComment(text = "会员等级,0:不是,1:普通会员,依次类推" )
    @Column(name = "vip" )
    private String vip;

    /**
     * 会员积分
     */
    @FiledComment(text = "会员积分" )
    @Column(name = "points" )
    private Integer points;

    /**
     * 可领红包数量
     */
    @FiledComment(text = "可领红包数量" )
    @Column(name = "redpacket_receive" )
    private Integer redpacketReceive;

    /**
     * 红包数量
     */
    @FiledComment(text = "红包数量" )
    @Column(name = "redpacket_amount" )
    private Integer redpacketAmount;

    /**
     * 红包金额
     */
    @FiledComment(text = "红包金额" )
    @Column(name = "redpacket_finance" )
    private BigDecimal redpacketFinance;

    /**
     * 是否完成新手任务
     */
    @FiledComment(text = "是否完成新手任务" )
    @Column(name = "new_task" )
    private Boolean newTask;

    /**
     * 是否完成特殊任务
     */
    @FiledComment(text = "是否完成特殊任务" )
    @Column(name = "special_task" )
    private Boolean specialTask;

    /**
     * 是否开启日常任务
     */
    @FiledComment(text = "是否开启日常任务" )
    @Column(name = "daily_task" )
    private Boolean dailyTask;

    /**
     * 是否确认：0：未确认，1：已确认
     */
    @FiledComment(text = "是否确认：0：未确认，1：已确认" )
    @Column(name = "isconfirm" )
    private String isconfirm;

    @Column(name = "create_time" )
    private Date createTime;

    @Column(name = "update_time" )
    private Date updateTime;

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

    public String getVip() {
        return this.vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRedpacketReceive() {
        return this.redpacketReceive;
    }

    public void setRedpacketReceive(Integer redpacketReceive) {
        this.redpacketReceive = redpacketReceive;
    }

    public Integer getRedpacketAmount() {
        return this.redpacketAmount;
    }

    public void setRedpacketAmount(Integer redpacketAmount) {
        this.redpacketAmount = redpacketAmount;
    }

    public BigDecimal getRedpacketFinance() { return redpacketFinance; }

    public void setRedpacketFinance(BigDecimal redpacketFinance) { this.redpacketFinance = redpacketFinance; }

    public Boolean getNewTask() {
        return this.newTask;
    }

    public void setNewTask(Boolean newTask) {
        this.newTask = newTask;
    }

    public Boolean getSpecialTask() {
        return this.specialTask;
    }

    public void setSpecialTask(Boolean specialTask) {
        this.specialTask = specialTask;
    }

    public Boolean getDailyTask() {
        return this.dailyTask;
    }

    public void setDailyTask(Boolean dailyTask) {
        this.dailyTask = dailyTask;
    }

    public String getIsconfirm() { return isconfirm; }

    public void setIsconfirm(String isconfirm) { this.isconfirm = isconfirm; }

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
