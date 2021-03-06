package com.ningyuan.mobile.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.mobile.model.ShopAttrKey;
import com.ningyuan.mobile.model.ShopAttrVal;
import com.ningyuan.mobile.model.ShopGoodsModel;
import com.ningyuan.mobile.model.ShopGoodsSkuModel;
import com.ningyuan.mobile.service.IAttrKeyService;
import com.ningyuan.mobile.service.IGoodsService;
import com.ningyuan.mobile.service.IShopGoodsSkuService;
import com.ningyuan.utils.Lists;
import com.ningyuan.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 11:16 AM
 */
@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IShopGoodsSkuService goodsSkuService;
    @Autowired
    private IAttrKeyService attrKeyService;

    /**
     * 获取指定类别下的商品列表
     *
     * @param idCategory
     * @return
     */
    @RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
    @ResponseBody
    public Object queryGoods(Long idCategory) {
//        PageHelper.startPage(Context.getHttpServletRequest());
        PageHelper.startPage(0, 10, true);
        return Rets.success(PageInfo.of(goodsService.getGoods(idCategory, true)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable Long id) {
        ShopGoodsModel goodsModel = new ShopGoodsModel();
        goodsModel.setId(id);
        ShopGoodsModel goods = goodsService.selectByPrimaryKey(goodsModel);
        Example example = new Example(ShopGoodsSkuModel.class);
        example.createCriteria().andEqualTo("idGoods", id);
        List<ShopGoodsSkuModel> skuList = goodsSkuService.selectByExample(example);

        Map skuMap = Maps.newHashMap();

        List<Map> tree = Lists.newArrayList();

        if (!skuList.isEmpty()) {
            List<ShopAttrVal> attrValList = Lists.newArrayList();

            Example example2 = new Example(ShopAttrKey.class);
            example2.createCriteria().andEqualTo("idCategory", goods.getIdCategory());
            List<ShopAttrKey> attrKeyList = attrKeyService.selectByExample(example2);

            for (ShopAttrKey attrKey : attrKeyList) {
                Map treeNode = Maps.newHashMap();
                treeNode.put("k", attrKey.getAttrName());
                List<Map> v = Lists.newArrayList();
                List<ShopAttrVal> attrValListChildren = attrKeyService.getAttrVals(attrKey.getId());
                attrValList.addAll(attrValListChildren);
                for (ShopAttrVal attrVal : attrValListChildren) {
                    v.add(Maps.newHashMap(
                            "id", attrVal.getId(),
                            "name", attrVal.getAttrVal(),
                            "plain", true
                    ));
                }
                treeNode.put("v", v);
                treeNode.put("k_s", "s" + attrKey.getId());
                tree.add(treeNode);
            }
            // Map<Long, ShopAttrVal> attrValMap = Lists.toMap(attrValList, "id");
            List<Map> skuList2 = Lists.newArrayList();

            for (ShopGoodsSkuModel sku : skuList) {
                Map oneSkuMap = Maps.newHashMap();
                oneSkuMap.put("id", sku.getId());
                oneSkuMap.put("price", sku.getPrice());
                /*String[] attrValIdArr = sku.getCode().split(",");
                for (String attrValId : attrValIdArr) {
                    ShopAttrVal attrVal = attrValMap.get(Long.valueOf(attrValId));
                    oneSkuMap.put("s" + attrVal.getIdAttrKey(), attrVal.getId());
                }*/
                oneSkuMap.put("code", sku.getCode());
                skuList2.add(oneSkuMap);
            }
            skuMap.put("list", skuList2);
            skuMap.put("price", skuList.get(0).getPrice());
            skuMap.put("collection_id", skuList.get(0).getId());
            skuMap.put("none_sku", false);
        } else {
            skuMap.put("price", goods.getPrice());
            skuMap.put("collection_id", goods.getId());
            skuMap.put("none_sku", true);
        }
        skuMap.put("tree", tree);
        skuMap.put("stock_num", goods.getStock());
        skuMap.put("hide_stock", true);
        return Rets.success(Maps.newHashMap(
                "goods", goods,
                "sku", skuMap
        ));
    }
}
