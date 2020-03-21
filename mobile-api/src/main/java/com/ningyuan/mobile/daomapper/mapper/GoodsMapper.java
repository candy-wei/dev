package com.ningyuan.mobile.daomapper.mapper;

import com.ningyuan.mobile.dto.GoodsDto;
import com.ningyuan.mobile.model.ShopGoodsModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsMapper extends Mapper<ShopGoodsModel> {
    List<GoodsDto> getGoods(@Param("idCategory") Long idCategory, @Param("isOnSale") Boolean isOnSale);
}
