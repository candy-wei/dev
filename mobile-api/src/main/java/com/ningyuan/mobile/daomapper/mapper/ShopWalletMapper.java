package com.ningyuan.mobile.daomapper.mapper;

import com.ningyuan.mobile.dto.RedPacketDto;
import tk.mybatis.mapper.common.Mapper;
import com.ningyuan.mobile.model.ShopWalletModel;

import java.util.List;

/**
 * generated by Generate Mapper.groovy 
 * <p>Date: Fri Apr 03 14:41:01 CST 2020.</p>
 *
 * @author (zengrc)
 */

public interface ShopWalletMapper extends Mapper<ShopWalletModel> {

    List<RedPacketDto> getCashList(String openId);
}