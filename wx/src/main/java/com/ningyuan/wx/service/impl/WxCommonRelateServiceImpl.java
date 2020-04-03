package com.ningyuan.wx.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.base.exception.ViewException;
import com.ningyuan.core.Conf;
import com.ningyuan.utils.ParamsUtils;
import com.ningyuan.utils.TemplateUtils;
import com.ningyuan.wx.daomapper.mapper.WxRelateMapper;
import com.ningyuan.wx.model.WxRelateModel;
import com.ningyuan.wx.service.IWxCommonRelateService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


/**
 *  wx_relate ,唯一键: openId + promoteType
 *  不适用 IWxGetByOpenId 接口
 *
 * @author (zengrc)
 */

@Service("wx")
public class WxCommonRelateServiceImpl extends BaseServiceImpl<WxRelateMapper, WxRelateModel> implements IWxCommonRelateService {

    @Override
    public boolean isUpdateRelate(String openId) {
        return true;
    }

    @Override
    public ModelAndView view(String openId, String state) {
        WxRelateModel wxRelateModel = this.selectByPrimaryKey(state);
        String url = TemplateUtils.replaceAll(Conf.get("shop.index.view.wx"), wxRelateModel);
        return new ModelAndView("redirect:" + ParamsUtils.getRomote() + url);
    }

    @Override
    public void updateRelateDef(String openId, String state) throws Exception {
        WxRelateModel relateModel = this.selectByPrimaryKey(state);
        WxRelateModel oldRelate = getByTypeOpenId(openId);
        if (!this.isUpdateRelate(openId) && oldRelate != null) {
            this.deleteByPrimaryKey(state);
            return;
        }

        if (oldRelate != null && !state.equals(( oldRelate).getId() + "")) {
            this.deleteByPrimaryKey(( oldRelate).getId());
        }

        relateModel.setOpenId(openId);
        this.updateByPrimaryKeySelective(relateModel);
    }

    @Override
    public WxRelateModel getByTypeOpenId(String openId) {
        WxRelateModel sel =new WxRelateModel();
        sel.setOpenId(openId);
        return this.selectOne(sel);
    }
}
