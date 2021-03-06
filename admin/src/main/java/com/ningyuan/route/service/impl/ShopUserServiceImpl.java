package com.ningyuan.route.service.impl;

import com.ningyuan.core.Conf;
import com.ningyuan.route.daomapper.mapper.ShopUserMapper;
import com.ningyuan.route.dto.SettingDto;
import com.ningyuan.route.dto.ShopMarketRatioDto;
import com.ningyuan.route.dto.ShopUserDto;
import com.ningyuan.route.dto.ShopUserQueryDto;
import com.ningyuan.route.service.IShopUserService;
import com.ningyuan.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Wed Mar 20 14:42:20 CST 2019.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopUserServiceImpl implements IShopUserService {

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Override
    public List<ShopUserDto> listConfirmUser(ShopUserQueryDto queryDto) {
        return shopUserMapper.listConfirmUser(queryDto);
    }

    @Override
    public List<ShopUserDto> listUnConfirmUser(ShopUserQueryDto queryDto) {
        return shopUserMapper.listUnConfirmUser(queryDto);
    }

    @Override
    public void updateConfirm(Long id) {
        shopUserMapper.updateConfirm(id);
    }

    @Override
    public void updateUserByid(ShopUserDto shopUserDto) {
        shopUserMapper.updateUserByid(shopUserDto);
    }

    @Override
    public List<ShopMarketRatioDto> getMarketRatio() {
        List<ShopMarketRatioDto> marketRatio = shopUserMapper.getMarketRatio();
        return marketRatio;
    }

    @Override
    public void updateSetting(List<ShopMarketRatioDto> marketRatio) {
        shopUserMapper.updateMarketRatio(marketRatio);
    }

    @Override
    public void addRedpacketAmount(Long id, String openId, Integer addAmount, Integer amount) {
        shopUserMapper.updateRedpacketAmount(id, amount);
        if (StringUtil.isNotEmpty(openId)) {
            shopUserMapper.insertRecord(openId, addAmount, Conf.get("wallet.receive.type:6"));
        }
    }

    @Override
    public void minusRedpacketAmount(Long id, Integer amount) {
        shopUserMapper.updateRedpacketAmount(id, amount);
    }

    @Override
    public Integer getShopCustomer(Long id) {
        return shopUserMapper.getCustomer(id);
    }
}
