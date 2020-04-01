package com.ningyuan.wx.model;

import com.ningyuan.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.InputStream;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Thu Nov 22 11:14:32 CST 2018.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="wx_user" )
public class WxUserModel extends BaseModel {

    @Column(name = "open_id" )
    private String openId;

    @Column(name = "union_id" )
    private String unionId;

    @Column(name = "nickname" )
    private InputStream nickname;

    @Column(name = "sex" )
    private Integer sex;

    @Column(name = "language" )
    private String language;

    @Column(name = "city" )
    private String city;

    @Column(name = "province" )
    private String province;

    @Column(name = "country" )
    private String country;

    @Column(name = "head_img_url" )
    private String headImgUrl;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() { return unionId; }

    public void setUnionId(String unionId) { this.unionId = unionId; }

    public InputStream getNickname() {
        return this.nickname;
    }

    public void setNickname(InputStream nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() { return headImgUrl; }

    public void setHeadImgUrl(String headImgUrl) { this.headImgUrl = headImgUrl; }
}
