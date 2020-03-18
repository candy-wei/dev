package com.ningyuan.wx.dto;


public class WxLocation {
  private long id;
  private long openId;
  private String lat;
  private String lng;
  private String nation;
  private String province;
  private String city;
  private String district;
  private String street;
  private String streetNumber;
  private java.sql.Timestamp createTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOpenId() {
    return openId;
  }

  public void setOpenId(long openId) {
    this.openId = openId;
  }


  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }


  public String getLng() {
    return lng;
  }

  public void setLng(String lng) {
    this.lng = lng;
  }


  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }


  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }


  public String getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

}
