package com.ningyuan.wx.utils;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.ningyuan.core.Conf;
import com.ningyuan.utils.CommonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.util.Map;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 9:58 on 2017/12/4.
 * @Modified by:
 */
public class WxCertUtils {

    protected static Logger log = LoggerFactory.getLogger(WxCertUtils.class);

    private static CloseableHttpClient getHttpClientByCert(String PKCS12, String certPath) throws Exception {
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(certPath));
        try {
            //指定PKCS12的密码
            keyStore.load(instream, PKCS12.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }
        //指定TLS版本
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, PKCS12.toCharArray()).build();
        //设置httpclient的SSLSocketFactory
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf) .build();
    }

    private static String postWithCert(String url, String xml, String certPath) throws Exception{
        //PKCS12的密码
        String PKCS12 = Conf.get("wx.partner");

        CloseableHttpClient httpClient = getHttpClientByCert(PKCS12, certPath);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            HttpPost httpPost = new HttpPost(url);
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            //InputStreamEntity严格是对内容和长度相匹配的。用法和BasicHttpEntity类似
            InputStreamEntity inputStreamEntity = new InputStreamEntity(is, is.available());
            httpPost.setEntity(inputStreamEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                org.apache.http.HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return stringBuffer.toString();
    }

    public static <T> T postWithCert(String url, String certPath, Object pramasObject, Class<T> resClass) throws Exception{
        Map params = CommonUtil.objectToMap(pramasObject);
        params.put("sign", WXPayUtil.generateSignature(params, Conf.get("wx.api.key")));
        String xml = WXPayUtil.mapToXml(params);
        String resXml = postWithCert(url, xml, certPath);
        log.info(resXml);
        Map<String, String> resMap = WXPayUtil.xmlToMap(resXml);
        return JSON.parseObject(JSON.toJSONString(resMap), resClass);
    }
}
