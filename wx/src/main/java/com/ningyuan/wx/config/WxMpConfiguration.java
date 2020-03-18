package com.ningyuan.wx.config;

import com.ningyuan.core.Conf;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信公众号主配置
 * <p>
 */
@Configuration
public class WxMpConfiguration {
    @Autowired
    WxMpHttpClientBuilder httpClientBuilder;

    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(Conf.get("wxsa.appId"));
        configStorage.setSecret(Conf.get("wxsa.appSecret"));
        configStorage.setToken(Conf.get("wxsa.token"));
        configStorage.setAesKey(Conf.get("wxsa.aesKey"));
        configStorage.setApacheHttpClientBuilder(httpClientBuilder);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());    //default
        return wxMpService;
    }
}
