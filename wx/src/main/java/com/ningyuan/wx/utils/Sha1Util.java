package com.ningyuan.wx.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.*;

/*
'============================================================================
'api说明：
'createSHA1Sign创建签名SHA1
'getSha1()Sha1签名
'============================================================================
'*/
public class Sha1Util {

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    //创建签名SHA1
    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();

        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        return getSha1(params);
    }

    //Sha1签名
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("GBK"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSign(SortedMap<String, String> signParams, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.append("key=" + key).toString();
        String md5Params = getSha1(params).toUpperCase();
        System.out.println("md5Params:" + md5Params);
        return md5Params;
    }

    public static String map2Sting(SortedMap<String, String> signParams) {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        System.out.println("sha1 sb:" + params);
        return params;
    }

    public static String MapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXMLTest2(map, sb);
        sb.append("</xml>");
        try {
            return new String(sb.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void mapToXMLTest2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXMLTest2(hm, sb);
                }

                sb.append("</" + key + ">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXMLTest2((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + ">" + value + "</" + key + ">");
                }

            }

        }
    }

    public static String postData(String urlStr, String data) {
        return postData(urlStr, data, null);
    }

    public static String postData(String urlStr, String data, String contentType) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            System.out.println("conn:" + conn.getURL());
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            /*if(StringUtils.isNotBlank(contentType))
                conn.setRequestProperty("content-type", contentType);*/
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            if (data == null) {
                data = "";
            }
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            System.out.println("Stringbuilder" + sb);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //logger_.error("Error connecting to " + urlStr + ": " + e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Auto-generated method stub
        String ss = "jsapi_ticket=bxLdikRXVbTPdHSM05e5u_TYBj0a_bMC8nK1aARl0B0FNu8_m0D7q5CC8UquMyiH3TIT_B2HSl2Bpo6YJ5Flvw&noncestr=test&timestamp=1527753326447&url=http://dasitest.com.ngrok.xiaomiqiu.cn/dasi/jsp/bg/referral/share.jsp";

       if(getSha1(ss).equals("55712a354c7699785e9abec62e8a73c20bf6c3cc")){
           System.out.println("========");
       } else {
           System.out.println(getSha1(ss));
       }

    }
}
