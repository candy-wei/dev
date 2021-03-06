package com.ningyuan.wx.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.wx.daomapper.mapper.WxsaCustomerMapper;
import com.ningyuan.wx.model.wxsa.WxsaCustomerModel;
import com.ningyuan.wx.service.IWxsaCustomerService;
import org.springframework.stereotype.Service;

/**
 * generated by Generate Service.groovy
 * <p>Date: Tue Aug 20 09:48:12 CST 2019.</p>
 *
 * @author (zengrc)
 */

@Service
public class WxsaCustomerServiceImpl extends BaseServiceImpl<WxsaCustomerMapper, WxsaCustomerModel> implements IWxsaCustomerService {

    @Override
    public void saveCustomer(String openId, WxsaCustomerModel customerModel) {
        WxsaCustomerModel exist = this.getByOpenId(openId);
        if (null == exist) {
            customerModel.setOpenId(openId);
            this.insertSelective(customerModel);
        } else {
            customerModel.setId(exist.getId());
            this.updateByPrimaryKeySelective(customerModel);
        }
    }
}
