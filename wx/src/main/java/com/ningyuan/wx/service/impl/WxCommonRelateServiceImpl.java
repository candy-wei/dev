package com.ningyuan.wx.service.impl;

import com.ningyuan.wx.daomapper.mapper.WxRelateMapper;
import com.ningyuan.wx.model.WxRelateModel;
import com.ningyuan.wx.service.IWxCommonRelateService;
import com.ningyuan.wx.service.IWxCommonRelateView;
import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.ParamsUtils;
import com.ningyuan.utils.TemplateUtils;
import org.apache.commons.lang3.StringUtils;
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
    public ModelAndView view(String promoteType, String openId, String state) {
        WxRelateModel wxRelateModel = this.selectByPrimaryKey(state);
        if(StringUtils.equals(wxRelateModel.getPromoteType(), Conf.get("wxsa.article.wx_relate.promoteType:wxsaArticle"))){
            return Context.getBean(wxRelateModel.getPromoteType(), IWxCommonRelateView.class).viewByType(wxRelateModel);
        }
        String url = TemplateUtils.replaceAll(Conf.get("promote.index.view.wx" + "." + wxRelateModel.getPromoteType()), wxRelateModel);
        return new ModelAndView("redirect:" + ParamsUtils.getRomote() + url);
    }

    @Override
    public void updateRelateDef(String openId, String state) throws Exception {
        WxRelateModel relateModel = this.selectByPrimaryKey(state);
        WxRelateModel oldRelate = getByTypeOpenId(relateModel.getPromoteType(),openId);
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
    public WxRelateModel getByTypeOpenId(String type, String openId) {
        WxRelateModel sel =new WxRelateModel();
        sel.setOpenId(openId);
        sel.setPromoteType(type);
        return this.selectOne(sel);
    }
}
