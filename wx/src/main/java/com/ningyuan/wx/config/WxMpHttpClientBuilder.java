package com.ningyuan.wx.config;

import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zongrui_cai
 * @Date: 2019/8/19 9:53
 */
@Component
public class WxMpHttpClientBuilder implements ApacheHttpClientBuilder {

    @Autowired
    private CloseableHttpClient httpClient;

    @Override
    public CloseableHttpClient build() {
        return httpClient;
    }

    @Override
    public ApacheHttpClientBuilder httpProxyHost(String httpProxyHost) {
        return this;
    }

    @Override
    public ApacheHttpClientBuilder httpProxyPort(int httpProxyPort) {
        return this;
    }

    @Override
    public ApacheHttpClientBuilder httpProxyUsername(String httpProxyUsername) {
        return this;
    }

    @Override
    public ApacheHttpClientBuilder httpProxyPassword(String httpProxyPassword) {
        return this;
    }

    @Override
    public ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory) {
        return this;
    }
}
