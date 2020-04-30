package com.ningyuan.route.dto;

public class ShopAddressDto {
    private Long id;
    private String nickname;
    private String receiver;
    private String mobile;
    private String address;
    private String province;
    private String city;
    private String area;
    private String defaultBoo;
    private String areaCode;
    private String postCode;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getProvince() { return province; }

    public void setProvince(String province) { this.province = province; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }

    public String getDefaultBoo() { return defaultBoo; }

    public void setDefaultBoo(String defaultBoo) { this.defaultBoo = defaultBoo; }

    public String getAreaCode() { return areaCode; }

    public void setAreaCode(String areaCode) { this.areaCode = areaCode; }

    public String getPostCode() { return postCode; }

    public void setPostCode(String postCode) { this.postCode = postCode; }
}
