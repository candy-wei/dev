package com.ningyuan.utils;

import com.alibaba.fastjson.JSON;
import com.ningyuan.core.Context;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static void printError(String errorCode, String errorMessage) {
        HttpServletResponse response = Context.getHttpServletResponse();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            Map<String,String> res =new HashMap<>();
            res.put("errorCode",errorCode);
            res.put("errorMsg",errorMessage);
            writer.print(JSON.toJSON(res).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
