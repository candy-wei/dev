package com.ningyuan.mobile.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 11:16 AM
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;
    /*@Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private AttrKeyService attrKeyService;*/

    /**
     * 获取指定类别下的商品列表
     *
     * @param idCategory
     * @return
     */
    @RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
    public Object queryGoods(@RequestParam("idCategory") Long idCategory) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return Rets.success(PageInfo.of(goodsService.getGoods(idCategory, true)));
    }

    /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        Goods goods = goodsService.get(id);
        List<GoodsSku> skuList = goodsSkuService.queryAll(Lists.newArrayList(
                SearchFilter.build("idGoods", id)
        ));


        Map skuMap = Maps.newHashMap();

        List<Map> tree = Lists.newArrayList();

        if (!skuList.isEmpty()) {
            List<AttrVal> attrValList = Lists.newArrayList();
            List<AttrKey> attrKeyList = attrKeyService.queryBy(goods.getIdCategory());
            for (AttrKey attrKey : attrKeyList) {
                Map treeNode = Maps.newHashMap();
                treeNode.put("k", attrKey.getAttrName());
                List<Map> v = Lists.newArrayList();
                List<AttrVal> attrValListChildren = attrKey.getAttrVals();
                attrValList.addAll(attrValListChildren);
                for (AttrVal attrVal : attrValListChildren) {
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
            Map<Long, AttrVal> attrValMap = Lists.toMap(attrValList, "id");
            List<Map> skuList2 = Lists.newArrayList();

            for (GoodsSku sku : skuList) {
                Map oneSkuMap = Maps.newHashMap();
                oneSkuMap.put("id", sku.getId());
                oneSkuMap.put("price", sku.getPrice());
                String[] attrValIdArr = sku.getCode().split(",");
                for (String attrValId : attrValIdArr) {
                    AttrVal attrVal = attrValMap.get(Long.valueOf(attrValId));
                    oneSkuMap.put("s" + attrVal.getIdAttrKey(), attrVal.getId());
                }
                oneSkuMap.put("stock_num", sku.getStock());
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
        skuMap.put("hide_stock", false);
        return Rets.success(Maps.newHashMap(
                "goods", goods,
                "sku", skuMap
        ));
    }*/
}
