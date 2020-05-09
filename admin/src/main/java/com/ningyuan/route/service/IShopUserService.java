package com.ningyuan.route.service;

import com.ningyuan.route.dto.SettingDto;
import com.ningyuan.route.dto.ShopMarketRatioDto;
import com.ningyuan.route.dto.ShopUserDto;
import com.ningyuan.route.dto.ShopUserQueryDto;

import java.util.List;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Wed Mar 20 14:42:20 CST 2019.</p>
 *
 * @author (zengrc)
 */

public interface IShopUserService {

    List<ShopUserDto> listConfirmUser(ShopUserQueryDto queryDto);

    List<ShopUserDto> listUnConfirmUser(ShopUserQueryDto queryDto);

    void updateConfirm(Long id);

    void updateUserByid(ShopUserDto shopUserDto);

    List<ShopMarketRatioDto> getMarketRatio();

    void updateSetting(List<ShopMarketRatioDto> marketRatio);

    void addRedpacketAmount(Long id, String openId, Integer addAmount, Integer amount);

    void minusRedpacketAmount(Long id, Integer amount);

    Integer getShopCustomer(Long id);
}
