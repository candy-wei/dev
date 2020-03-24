package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.model.ShopAddressModel;
import com.ningyuan.mobile.service.IShopAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user/address")
public class AddressController extends BaseController {

    @Autowired
    private IShopAddressService addressService;

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable("id") Long id){
        ShopAddressModel shopAddressMode = new ShopAddressModel();
        if (id != null) {
            String openId = Context.getOpenId();
            shopAddressMode.setOpenId(openId);
            shopAddressMode.setId(id);
        }
        return Rets.success(addressService.selectByPrimaryKey(shopAddressMode));

    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object remove(@PathVariable("id") Long id){
        ShopAddressModel shopAddressMode = new ShopAddressModel();
        if (id != null) {
            String openId = Context.getOpenId();
            shopAddressMode.setOpenId(openId);
            shopAddressMode.setId(id);
            addressService.deleteByPrimaryKey(shopAddressMode);
        }
        return Rets.success();
    }

    @RequestMapping(value = "{id}/{isDefault}",method = RequestMethod.POST)
    @ResponseBody
    public Object changeDefault(@PathVariable("id") Long id,@PathVariable("isDefault") Boolean isDefault){
        String openId = Context.getOpenId();
        ShopAddressModel queryModel = new ShopAddressModel();
        queryModel.setOpenId(openId);
        queryModel.setIsDefault(Boolean.TRUE);
        ShopAddressModel defaultAddr = addressService.selectOne(queryModel);
        if(defaultAddr != null){
            if(defaultAddr.getId().equals(id)){
                defaultAddr.setIsDefault(isDefault);
                addressService.updateByPrimaryKeySelective(defaultAddr);
                return Rets.success();
            }else{
                if(isDefault) {
                    defaultAddr.setIsDefault(false);
                    addressService.updateByPrimaryKeySelective(defaultAddr);
                }
            }
        }

        ShopAddressModel queryModel2 = new ShopAddressModel();
        queryModel2.setOpenId(openId);
        queryModel2.setId(id);
        ShopAddressModel address = addressService.selectLimitOne(queryModel2);
        address.setIsDefault(isDefault);
        addressService.updateByPrimaryKeySelective(address);
        return Rets.success(address);

    }

    @RequestMapping(value = "/queryByUser",method = RequestMethod.GET)
    @ResponseBody
    public Object getByUser(){
        String openId = Context.getOpenId();
        Example example = new Example(ShopAddressModel.class);
        example.createCriteria().andEqualTo("openId", openId).andEqualTo("isDelete", Boolean.FALSE);
        List<ShopAddressModel> list = addressService.selectByExample(example);
        return Rets.success(list);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestBody @Valid ShopAddressModel addressInfo){
        String openId = Context.getOpenId();
        addressInfo.setOpenId(openId);
        if(addressInfo.getId()!=null){
            addressService.updateByPrimaryKeySelective(addressInfo);
        }else{
            addressService.insertSelective(addressInfo);
        }

        return Rets.success();
    }
}
