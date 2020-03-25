package com.ningyuan.wx.handler;

import com.alibaba.fastjson.JSON;
import com.ningyuan.core.Conf;
import com.ningyuan.utils.RESTUtils;
import com.ningyuan.wx.constant.WxsaConstant;
import com.ningyuan.wx.model.WxRelateModel;
import com.ningyuan.wx.model.wxsa.WxsaCustomerModel;
import com.ningyuan.wx.model.wxsa.WxsaOutMsgModel;
import com.ningyuan.wx.model.wxsa.WxsaSubscribeModel;
import com.ningyuan.wx.service.IWxCommonRelateService;
import com.ningyuan.wx.service.IWxsaCustomerService;
import com.ningyuan.wx.service.IWxsaOutMsgService;
import com.ningyuan.wx.service.IWxsaSubscribeService;
import com.ningyuan.wx.utils.WxsaUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.message.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author: zongrui_cai
 * @Date: 2019/8/12 14:11
 */
@Component
public class WxsaOutMsgHandler {
    @Autowired
    private IWxsaOutMsgService outMsgService;
    @Autowired
    private IWxsaSubscribeService subscribeService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IWxCommonRelateService commonRelateService;
    @Autowired
    private IWxsaCustomerService customerService;


    protected Logger logger = LoggerFactory.getLogger(getClass());

    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage) throws Exception {
        //统计 菜单点击量
        if (StringUtils.equals(wxMessage.getMsgType(), WxConsts.XmlMsgType.EVENT)
                && StringUtils.equalsAny(wxMessage.getEvent(), WxConsts.EventType.VIEW, WxConsts.EventType.CLICK)) {
            String keyShort = StringUtils.substringAfter(wxMessage.getEventKey(), "dasijiaoyu.com");
            String key = StringUtils.isNotBlank(keyShort) ? keyShort : wxMessage.getEventKey();
        }

        //过滤掉某些事件推送
        if (StringUtils.equals(wxMessage.getMsgType(), WxConsts.XmlMsgType.EVENT)
                && StringUtils.equalsAny(wxMessage.getEvent(), WxConsts.EventType.VIEW)) {
            return null;
        }
        logger.info("in Message:{}", JSON.toJSONString(wxMessage));
        //关注事件,处理来源关系
        this.handleSubscribe(wxMessage);

        //取消关注
        if (StringUtils.equalsIgnoreCase(wxMessage.getEvent(), WxConsts.EventType.UNSUBSCRIBE)) {
            this.updateUnSubscribe(wxMessage.getFromUser());
            return null;
        }

        //响应消息
        WxMpXmlOutMessage out = this.responseWxsaOutMsg(wxMessage);
        if (out != null) {
            return out;
        }
        if (StringUtils.equalsAny(wxMessage.getMsgType(),
                WxConsts.XmlMsgType.TEXT, WxConsts.XmlMsgType.VOICE, WxConsts.XmlMsgType.IMAGE)) {
            return handleToKefu(wxMessage); //普通消息,转发客服
        }
        return null;
    }

    //根据接收到的消息, 查数据库wxsaOutMsg, 响应消息
    private WxMpXmlOutMessage responseWxsaOutMsg(WxMpXmlMessage wxMessage) throws WxErrorException, IOException {
        String msgType = wxMessage.getMsgType();
        String event = wxMessage.getEvent();
        WxsaOutMsgModel model;
        if (StringUtils.equals(msgType, WxConsts.XmlMsgType.TEXT)) {
            // 限制关键词长度,避免多余的数据库查询
            int maxLen = Integer.parseInt(Conf.get("wxsa.outMsg.keyword.maxLen:10"));
            if (StringUtils.length(wxMessage.getContent()) > maxLen) {
                return null;
            }
            //关键词 自动回复
            Example example = new Example(WxsaOutMsgModel.class);
            example.createCriteria()
                    .andEqualTo("inEventType", event)
                    .andEqualTo("inMsgType", msgType)
                    .andLike("keyWord", "%" + wxMessage.getContent() + "%");
            model = outMsgService.selectLimitOneByExample(example);
        } else {
            WxsaOutMsgModel selModel = new WxsaOutMsgModel();
            selModel.setInEventType(event);
            selModel.setInMsgType(msgType);
            if (!StringUtils.equalsIgnoreCase(wxMessage.getEvent(), WxConsts.EventType.SUBSCRIBE)
                    && StringUtils.isNotEmpty(wxMessage.getEventKey())) {
                selModel.setInEventKey(wxMessage.getEventKey());
            }
            model = outMsgService.selectLimitOne(selModel);

        }

        if (model != null) {
            WxMpXmlOutMessage res;
            if (StringUtils.equalsIgnoreCase(model.getOutMsgType(), WxConsts.XmlMsgType.TEXT)) {    //纯文本
                res = new WxMpXmlOutTextMessage();
                ((WxMpXmlOutTextMessage) res).setContent(model.getContent());
            } else if (StringUtils.equalsIgnoreCase(model.getOutMsgType(), WxConsts.XmlMsgType.NEWS)) { //图文
                res = new WxMpXmlOutNewsMessage();
                List<WxMpXmlOutNewsMessage.Item> articles = JSON.parseArray(model.getArticles(), WxMpXmlOutNewsMessage.Item.class);
                ((WxMpXmlOutNewsMessage) res).getArticles().addAll(articles);
                ((WxMpXmlOutNewsMessage) res).setArticleCount(((WxMpXmlOutNewsMessage) res).getArticles().size());
            } else if (StringUtils.equalsIgnoreCase(model.getOutMsgType(), WxConsts.XmlMsgType.IMAGE)) {
                res = new WxMpXmlOutImageMessage();
                if (StringUtils.isEmpty(model.getMediaId())) {
                    WxMpMaterial wxMaterial = new WxMpMaterial();
                    ResponseEntity<byte[]> downloadRes = RESTUtils.rest().getForEntity(model.getContent(), byte[].class);
                    File tempFile = FileUtils.createTmpFile(new ByteArrayInputStream(downloadRes.getBody())
                            , UUID.randomUUID().toString(), StringUtils.substringAfterLast(model.getContent(), "."));
                    logger.info("tempFile:{}", tempFile.getAbsolutePath());
                    wxMaterial.setFile(tempFile);
                    wxMaterial.setName(model.getInEventType() + model.getInEventKey());
                    WxMpMaterialUploadResult uploadResult = wxMpService.getMaterialService().materialFileUpload(WxConsts.MediaFileType.IMAGE, wxMaterial);
                    logger.info("uploadResult:{}", JSON.toJSONString(uploadResult));
                    model.setMediaId(uploadResult.getMediaId());
                    boolean deleteRes = tempFile.delete();
                    logger.info("tempFile deleteRes:{}", deleteRes);
                    outMsgService.updateByPrimaryKeySelective(model);
                }
                ((WxMpXmlOutImageMessage) res).setMediaId(model.getMediaId());
            } else {
                return null;
            }
            res.setFromUserName(wxMessage.getToUser());
            res.setToUserName(wxMessage.getFromUser());
            res.setCreateTime(System.currentTimeMillis() / 1000L);
            return res;
        }
        return null;
    }

    //转发客服
    private WxMpXmlOutMessage handleToKefu(WxMpXmlMessage wxMessage) {
        return WxMpXmlOutMessage
                .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
    }

    //关注
    private void handleSubscribe(WxMpXmlMessage wxMessage) throws Exception {
        if (StringUtils.equalsIgnoreCase(wxMessage.getEvent(), WxConsts.EventType.SUBSCRIBE)) {
            String openId = wxMessage.getFromUser();
            String sceneId = StringUtils.substringAfter(wxMessage.getEventKey(), "qrscene_");
            WxsaSubscribeModel exist = subscribeService.getByOpenId(openId);
            WxsaSubscribeModel subscribeModel = new WxsaSubscribeModel();
            subscribeModel.setHasUnsubscribe(Boolean.FALSE);
            subscribeModel.setOpenId(openId);
            if (StringUtils.isNotEmpty(sceneId)) {
                subscribeModel.setSceneId(sceneId);
                // TODO 来源的问题
                subscribeModel.setSourceId(Conf.get("ningyuan.default.sourceId"));
            }
            if (exist == null) {
                subscribeService.insertSelective(subscribeModel);
            } else {
                subscribeModel.setId(exist.getId());
                subscribeService.updateByPrimaryKeySelective(subscribeModel);
            }
            //update wxUser.remark
            if (StringUtils.isNotEmpty(sceneId)) {
                String remark = "wxsa" + subscribeModel.getId() + "wxsa";
                wxMpService.getUserService().userUpdateRemark(openId, remark);
                logger.info("wx_user,after updateRemark:{}", wxMpService.getUserService().userInfo(openId));
            }
            //设置 parentOpenId, articleId
            WxRelateModel commonRelateModel = commonRelateService.getByTypeOpenId(
                    Conf.get("wxsa.article.wx_relate.promoteType:wxsaArticle"), openId);
            if (commonRelateModel != null) {
                String countermanId = WxsaUtils.getFromCommonParams(commonRelateModel.getParams(), WxsaConstant.commonRelate.PARAMS_COUNTERMAN_ID);
                if (StringUtils.isNotEmpty(countermanId)) {
                    WxsaCustomerModel selCustomer = new WxsaCustomerModel();
                    selCustomer.setCountermanId(countermanId);
                    WxsaCustomerModel parent = customerService.selectOne(selCustomer);
                    if (parent != null) {
                        WxsaCustomerModel customerModel = new WxsaCustomerModel();
                        String articleId = WxsaUtils.getFromCommonParams(commonRelateModel.getParams(), WxsaConstant.commonRelate.PARAMS_ARTICLE_ID);
                        customerModel.setArticleId(articleId);
                        customerModel.setParentOpenId(parent.getOpenId());
                        customerService.saveCustomer(openId, customerModel);
                    } else {
                        logger.error("parent no found,countermanId:{}", countermanId);
                    }
                }
            }
        }
    }

    //取消关注
    private void updateUnSubscribe(String openId) {
        WxsaSubscribeModel subscribeModel = subscribeService.getByOpenId(openId);
        if (subscribeModel == null) {
            return;
        }
        subscribeModel.setHasUnsubscribe(Boolean.TRUE);
        subscribeService.updateByPrimaryKeySelective(subscribeModel);
    }
}
