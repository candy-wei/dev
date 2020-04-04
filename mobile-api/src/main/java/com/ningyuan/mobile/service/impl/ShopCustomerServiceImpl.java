package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.ShopCustomerMapper;
import com.ningyuan.mobile.dto.UserInfoDto;
import com.ningyuan.mobile.model.ShopCustomerModel;
import com.ningyuan.mobile.model.ShopOrderModel;
import com.ningyuan.mobile.service.IShopCustomerService;
import com.ningyuan.wx.model.WxRelateModel;
import com.ningyuan.wx.service.IWxCommonRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Fri Apr 03 11:08:59 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopCustomerServiceImpl extends BaseServiceImpl<ShopCustomerMapper, ShopCustomerModel> implements IShopCustomerService {
    @Autowired
    private IWxCommonRelateService commonRelateService;

    @Override
    public void updateCustomer(ShopOrderModel orderModel) {
        // TODO 根据支付结果更新customer
        ShopCustomerModel customerModel = new ShopCustomerModel();
        customerModel.setOpenId(orderModel.getOpenId());
        ShopCustomerModel updateModel = this.selectLimitOne(customerModel);
        updateCustomerModel(updateModel);

//        查找wx_relate表的关联关系，给上一级的客户加积分，更新customer
        WxRelateModel relateModel = commonRelateService.getByOpenId(orderModel.getOpenId());
        if (!StringUtils.isEmpty(relateModel.getParentOpenId())) {
            updateParentCustomer(relateModel.getParentOpenId());
        }
    }

    private void updateParentCustomer(String parentOpenId) {
        // TODO 这里有问题的
        ShopCustomerModel customerModel = new ShopCustomerModel();
        customerModel.setOpenId(parentOpenId);
        ShopCustomerModel model = this.selectLimitOne(customerModel);
        model.setPoints(model.getPoints() + 1);
        model.setRedpacketAmount(model.getRedpacketAmount() + 10);

        if (model.getNewTask()) {
            model.setSpecialTask(Boolean.TRUE);
            model.setRedpacketAmount(model.getRedpacketAmount() + 90);
        }else {
            model.setNewTask(Boolean.TRUE);
        }
        model.setRedpacketReceive(model.getRedpacketReceive() + 1);
        this.updateByPrimaryKeySelective(model);
    }

    private void updateCustomerModel(ShopCustomerModel updateModel) {
        updateModel.setPoints(updateModel.getPoints() + 1);
        updateModel.setRedpacketAmount(updateModel.getRedpacketAmount() + 10);
        updateModel.setRedpacketReceive(updateModel.getRedpacketReceive() + 1);
        this.updateByPrimaryKeySelective(updateModel);
    }

    @Override
    public UserInfoDto queryUserInfo(String openId) {
        return this.mapper.queryUserInfo(openId);
    }
}
