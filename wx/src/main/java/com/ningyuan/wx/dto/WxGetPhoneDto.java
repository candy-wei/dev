package com.ningyuan.wx.dto;

/***
 * Author: YeJian
 * Create on: 2019/5/20
 * Email: amazeconch@gmail.com
 * Explanation: 该类的具体描述
 */
public class WxGetPhoneDto {

    /**
     * phoneNumber : 13580006666
     * purePhoneNumber : 13580006666
     * countryCode : 86
     * watermark : {"appid":"APPID","timestamp":"TIMESTAMP"}
     */

    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private WatermarkBean watermark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public WatermarkBean getWatermark() {
        return watermark;
    }

    public void setWatermark(WatermarkBean watermark) {
        this.watermark = watermark;
    }

    public static class WatermarkBean {
        /**
         * appid : APPID
         * timestamp : TIMESTAMP
         */

        private String appid;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
