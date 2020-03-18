package com.ningyuan.utils;

import com.alibaba.fastjson.JSON;
import com.ningyuan.core.Conf;

import java.util.Arrays;

public class MaskUtils {

    private Integer preLength;

    private Integer sufLength;

    private Integer maskLength;

    private char mask;

    private Integer resultLength;

    private MaskUtils(){}

    public static MaskUtils build(String maskKey){
        MaskUtils maskDto = JSON.parseObject(Conf.get(maskKey), MaskUtils.class);
        maskDto.setResultLength(maskDto.getPreLength() + maskDto.getMaskLength() + maskDto.getSufLength());
        return maskDto;
    }

    public String mask(String maskTarget){
        if(maskTarget.length() <= this.resultLength){
            return maskTarget;
        }

        char[] masks = new char[this.maskLength];
        Arrays.fill(masks, this.mask);
        String pattern = "(^.{" + this.getPreLength() + "}).*?(.{" + this.getSufLength() + "}$)";
        return  maskTarget.replaceAll(pattern, "$1" + new String(masks) + "$2");
    }

    public Integer getPreLength() {
        return preLength;
    }

    public void setPreLength(Integer preLength) {
        this.preLength = preLength;
    }

    public Integer getSufLength() {
        return sufLength;
    }

    public void setSufLength(Integer sufLength) {
        this.sufLength = sufLength;
    }

    public Integer getMaskLength() {
        return maskLength;
    }

    public void setMaskLength(Integer maskLength) {
        this.maskLength = maskLength;
    }

    public char getMask() {
        return mask;
    }

    public void setMask(char mask) {
        this.mask = mask;
    }

    public Integer getResultLength() {
        return resultLength;
    }

    public void setResultLength(Integer resultLength) {
        this.resultLength = resultLength;
    }
}
