package com.ningyuan.route.service.impl;

import com.ningyuan.core.Conf;
import com.ningyuan.route.daomapper.mapper.ShopUserMapper;
import com.ningyuan.route.dto.SettingDto;
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
    public SettingDto getSettingDto() {
        String performanceRatio = shopUserMapper.getPerformanceRatio();
        String dividendRatio = shopUserMapper.getDividendRatio();
        SettingDto settingDto = new SettingDto();
        settingDto.setPerformance_ratio(performanceRatio);
        settingDto.setDividend_ratio(dividendRatio);
        return settingDto;
    }

    @Override
    public void updateSetting(SettingDto settingDto) {
        shopUserMapper.updateSetting(settingDto);
    }

    @Override
    public void updateRedpacketAmount(Long id, String openId, String redpacketAmount) {
        shopUserMapper.updateRedpacketAmount(id, redpacketAmount);
        if (StringUtil.isNotEmpty(openId)) {
            shopUserMapper.insertRecord(openId, redpacketAmount, Conf.get("wallet.receive.type:6"));
        }
    }
}
