package com.ningyuan.wx.utils;

import com.alibaba.fastjson.JSONObject;
import com.ningyuan.wx.model.WxLocationModel;
import com.ningyuan.core.Conf;
import com.ningyuan.utils.RESTUtils;
import com.ningyuan.utils.TemplateUtils;
import org.springframework.util.DigestUtils;

import java.util.Date;

public class WxLocationUtils {

    public  static WxLocationModel transform(String lat, String lng) throws Exception {
        String key = Conf.get("wx.location.key");
        String secretKey = Conf.get("wx.location.secretKey");
        String baseUrl = Conf.get("wx.location.url");
        String sigPre = Conf.get("wx.location.sig");
        String sig = DigestUtils.md5DigestAsHex(TemplateUtils.replaceAll(sigPre, key, lat, lng, secretKey).getBytes());
        String url = TemplateUtils.replaceAll(baseUrl, key, lat, lng, sig);

        JSONObject root = RESTUtils.get(url, JSONObject.class).getBody();
        if (root.getInteger("status") == 0) {
            WxLocationModel locationModel = new WxLocationModel();
            locationModel.setCreateTime(new Date());
            locationModel.setLat(lat);
            locationModel.setLng(lng);
            JSONObject resultJson = root.getJSONObject("result");
            JSONObject addr = resultJson.getJSONObject("address_component");
            locationModel.setNation(addr.getString("nation"));
            locationModel.setProvince(addr.getString("province"));
            locationModel.setCity(addr.getString("city"));
            locationModel.setDistrict(addr.getString("district"));
            locationModel.setStreet(addr.getString("street"));
            locationModel.setStreetNumber(addr.getString("street_number"));
            return locationModel;
        }
        throw new Exception("经纬度转换位置失败");
    }

}
