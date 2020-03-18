package com.ningyuan.wx.service.impl;

import com.ningyuan.wx.daomapper.mapper.WxUserMapper;
import com.ningyuan.wx.dto.GetTokenByCodeResultDto;
import com.ningyuan.wx.dto.WxUserDto;
import com.ningyuan.wx.model.WxUserModel;
import com.ningyuan.wx.service.IWxUserService;
import com.ningyuan.wx.utils.WxUtils;
import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.core.Context;
import com.ningyuan.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * generated by Generate Service.groovy
 * <p>Date: Wed Oct 24 09:50:21 CST 2018.</p>
 *
 * @author (zengrc)
 */

@Service
public class WxUserServiceImpl extends BaseServiceImpl<WxUserMapper, WxUserModel> implements IWxUserService {

    @Override
    public WxUserDto getDtoByOpenId(String openId) {
        WxUserModel wxUserModel = this.getByOpenId(openId);
        WxUserDto wxUserDto = new WxUserDto();
        BeanUtils.copyProperties(wxUserModel, wxUserDto);
        wxUserDto.setNickname(CommonUtil.inputStream2Str(wxUserModel.getNickname()));
        return wxUserDto;
    }

    @Override
    public void saveWxUser(GetTokenByCodeResultDto getTokenByCodeResultDto, String promoteType) {
        try {
            WxUserModel wxUserModel = new WxUserModel();
            wxUserModel.setOpenId(getTokenByCodeResultDto.getOpenid());
            wxUserModel = this.selectOne(wxUserModel);

            if (wxUserModel != null && !StringUtils.equals(wxUserModel.getPromoteType(), promoteType)) {
                this.deleteByPrimaryKey(wxUserModel);
                wxUserModel = null;
            }

            if (wxUserModel == null) {
                wxUserModel = WxUtils.getServerUserInfo("wx.user.info.base", getTokenByCodeResultDto.getOpenid(), getTokenByCodeResultDto.getAccess_token());
                wxUserModel.setUserAgent(Context.getHttpServletRequest().getHeader("User-Agent"));
                wxUserModel.setPromoteType(promoteType);
                this.insertSelective(wxUserModel);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
