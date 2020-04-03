package com.ningyuan.wx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ningyuan.annotation.AspectBefore;
import com.ningyuan.base.BaseModel;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.ParamsUtils;
import com.ningyuan.utils.RESTUtils;
import com.ningyuan.utils.TemplateUtils;
import com.ningyuan.wx.model.WxNotifyInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface IWxRelateService<T> extends IWxGetByOpenId<T> {
    Logger logger = LoggerFactory.getLogger(IWxRelateService.class);

    default T getRelateByOpenId(String openId) {
        return this.getByOpenId(openId);
    }

    default String saveRelate(HttpServletRequest request) throws Exception {
        Map<String, String> params = ParamsUtils.requestParams2Map(request.getParameterMap());
        params.remove("openId");
        return saveRelate(params);
    }

    @AspectBefore(handle = "saveRelateTest")
    default String saveRelate(Map<String, String> params) throws Exception {
        Class<T> tClass = getRelateClass();
        T relateModel = ParamsUtils.mapToObject(params, tClass);
        this.insertSelective(relateModel);
        return (long) ((BaseModel) relateModel).getId() + "";
    }

    boolean isUpdateRelate(String openId);

    default void updateRelate(String openId, String state) throws Exception {
        updateRelateDef(openId, state);
    }

    default void updateRelateDef(String openId, String state) throws Exception {
        T oldRelate = this.getRelateByOpenId(openId);
        if (!this.isUpdateRelate(openId) && oldRelate != null) {
            this.deleteByPrimaryKey(state);
            return;
        }

        if (oldRelate != null && !state.equals(((BaseModel) oldRelate).getId() + "")) {
            this.deleteByPrimaryKey(((BaseModel) oldRelate).getId());
        }

        T relateModel = this.selectByPrimaryKey(state);
        Field field = relateModel.getClass().getDeclaredField("openId");
        field.setAccessible(true);
        field.set(relateModel, openId);
        this.updateByPrimaryKeySelective(relateModel);
        Context.addTreadLocal(this.getClass().getName() + openId, relateModel);
    }

    default Map<String, Object> genShareParams(String openId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentOpenId", openId);
        return params;
    }

    default void verify(String openId) throws Exception {
    }

    default void preHandle(String openId, String id) throws Exception {
    }

    @AspectBefore(handle = "paySuccessTest")
    default void preHandlePaySuccess(Map<String, String> reqData) throws Exception {
    }

    default void handlePaySuccess(final Map<String, String> reqData) throws Exception {
        try {
            WxNotifyInfoModel payNotifyModel = new WxNotifyInfoModel();
            payNotifyModel.setAppointId(reqData.get("attach"));
            payNotifyModel.setOpenId(reqData.get("openid"));
            payNotifyModel.setTimeEnd(reqData.get("time_end"));
            payNotifyModel.setTransactionId(reqData.get("transaction_id"));
            Context.getBean(IWxNotifyInfoService.class).insert(payNotifyModel);
        } catch (Exception e) {
            logger.error("插入WxNotifyInfo失败", e);
        }

        this.preHandlePaySuccess(reqData);
        final String notifyUrl = Conf.get("promote.pay.notify.url");
        Context.getBean(ThreadPoolTaskExecutor.class).execute(() -> {
            if (isNotifyHtPaySuccess()) {
                RESTUtils.post(notifyUrl, ErrorMessage.class, reqData);
            }
            try {
                this.afterHandlePaySuccess(reqData);
            } catch (Exception e) {
                logger.error("支付后处理失败", e);
            }
        });
    }

    default boolean isNotifyHtPaySuccess() {
        return true;
    }

    default void afterHandlePaySuccess(Map<String, String> reqData) throws Exception {
    }

    /**
     * 支付金额
     * @return
     */
    default String getPayPrice(String openId) throws StatelessException {
        // 计算支付金额
        return "111";
    }

    default ModelAndView view(String openId, String state) {
        return new ModelAndView("redirect:" + ParamsUtils.getRomote() + TemplateUtils.replaceAll(Conf.get("shop.index.view"), openId));
    }

    /**
     * 获得分享受人手机号
     * @return
     */
    default String getParentMobile()  {
        T o = getByOpenId(Context.getTreadLocal("openId"));
        try {
            JSONObject json =JSONObject.parseObject(JSON.toJSONString(o));
            String parentOpenId =json.getString("parentOpenId");
            IWxGetByOpenId wxGetByOpenId = Context.getBean(Context.getTreadLocal("promoteType") + "CustomerServiceImpl", IWxGetByOpenId.class);
            Object parent = wxGetByOpenId.getByOpenId(parentOpenId);
            JSONObject parentJson =JSONObject.parseObject(JSON.toJSONString(parent));
            return parentJson.getString("mobile");
        } catch (Exception e){
            logger.error("获取分享人手机号失败", e);
        }
        return null;
    }
}
