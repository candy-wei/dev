package com.ningyuan.utils;

import java.util.UUID;

/**
 * 生成GUID
 *
 * @author 冯华强
 * @version 1.0
 *          2011-3-21
 */
public class CreateGUID {

    /**
     * 生成GUID
     *
     * @return GUID
     */
    public static String createGuId() {
        UUID uuid = UUID.randomUUID();
        String uuIdStr = uuid.toString();
        uuIdStr = uuIdStr.replace("-", "");
        return uuIdStr;
    }
}
