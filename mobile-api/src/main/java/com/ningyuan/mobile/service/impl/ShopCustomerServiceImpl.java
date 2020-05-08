package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.core.Conf;
import com.ningyuan.mobile.daomapper.mapper.ShopCustomerMapper;
import com.ningyuan.mobile.dto.*;
import com.ningyuan.mobile.model.ShopCustomerModel;
import com.ningyuan.mobile.model.ShopOrderModel;
import com.ningyuan.mobile.model.ShopReceiveRecordModel;
import com.ningyuan.mobile.model.ShopWalletModel;
import com.ningyuan.mobile.service.IShopCustomerService;
import com.ningyuan.mobile.service.IShopReceiveRecordService;
import com.ningyuan.mobile.service.IShopWalletService;
import com.ningyuan.utils.StringUtil;
import com.ningyuan.wx.model.WxRelateModel;
import com.ningyuan.wx.service.IWxCommonRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Fri Apr 03 11:08:59 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopCustomerServiceImpl extends BaseServiceImpl<ShopCustomerMapper, ShopCustomerModel> implements IShopCustomerService {
    @Autowired
    private IWxCommonRelateService commonRelateService;

    @Autowired
    private IShopWalletService walletService;

    @Autowired
    private IShopReceiveRecordService recordService;

    @Override
    public void updateCustomer(ShopOrderModel orderModel) {
        // TODO 根据支付结果更新customer
        /*ShopCustomerModel customerModel = new ShopCustomerModel();
        customerModel.setOpenId(orderModel.getOpenId());
        ShopCustomerModel updateModel = this.selectLimitOne(customerModel);
        updateCustomerModel(updateModel);*/

//        查找wx_relate表的关联关系，给上一级的客户加积分，更新customer
       // WxRelateModel relateModel = commonRelateService.getByOpenId(orderModel.getOpenId());
        /*if (!StringUtils.isEmpty(relateModel.getParentOpenId())) {
            updateParentCustomer(relateModel.getParentOpenId());
        }*/
    }

    private void updateParentCustomer(String parentOpenId) {
        // TODO 这里有问题的
        ShopCustomerModel customerModel = new ShopCustomerModel();
        customerModel.setOpenId(parentOpenId);
        ShopCustomerModel model = this.selectLimitOne(customerModel);
        model.setPoints(model.getPoints() + 1);
        model.setRedpacketAmount(model.getRedpacketAmount() + 10);

        if (model.getNewTask()) {
            model.setSpecialTask(Boolean.TRUE);
            model.setRedpacketAmount(model.getRedpacketAmount() + 90);
        }else {
            model.setNewTask(Boolean.TRUE);
        }
        model.setRedpacketReceive(model.getRedpacketReceive() + 1);
        this.updateByPrimaryKeySelective(model);
    }

    private void updateCustomerModel(ShopCustomerModel updateModel) {
        updateModel.setPoints(updateModel.getPoints() + 1);
        updateModel.setRedpacketAmount(updateModel.getRedpacketAmount() + 10);
        updateModel.setRedpacketReceive(updateModel.getRedpacketReceive() + 1);
        this.updateByPrimaryKeySelective(updateModel);
    }

    @Override
    public UserInfoDto queryUserInfo(String openId) {
        return this.mapper.queryUserInfo(openId);
    }

    @Override
    public ShopCustomerModel checkuser(String openId) {
        Example example = new Example(ShopCustomerModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("openId", openId);
        criteria.andGreaterThan("redpacketReceive", "0");
        criteria.andGreaterThan("redpacketFinance", "0");
        criteria.andGreaterThan("redpacketAmount", "0");
        return this.mapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor  = Exception.class)
    public String openRedpacket(String openId) {
        // 10个红包，总共发20元
        ShopRedpacketDto redpacket = this.mapper.getRedpacket(openId);
        double randomMoney = getRandomMoney(redpacket);
        BigDecimal bigDecimalMoney = BigDecimal.valueOf(randomMoney);
        this.updateRedpacket(openId, redpacket);
        ShopWalletModel walletModel =new ShopWalletModel();
        walletModel.setOpenId(openId);
        ShopWalletModel existModel = walletService.selectLimitOne(walletModel);
        if (existModel == null) {
            walletModel.setFinance(bigDecimalMoney.toString());
            walletService.insertSelective(walletModel);
        }else {
            double sumFinance = add(existModel.getFinance(), bigDecimalMoney.toString());
            existModel.setFinance(sumFinance + "");
            walletService.updateByPrimaryKeySelective(existModel);
        }
        ShopReceiveRecordModel recordModel = new ShopReceiveRecordModel();
        recordModel.setAmount(bigDecimalMoney.toString());
        recordModel.setOpenId(openId);
        recordModel.setOptType(Conf.get("shop.red.packet.type:1"));
        recordService.insertSelective(recordModel);
        recordModel = new ShopReceiveRecordModel();
        recordModel.setAmount("1");
        recordModel.setOpenId(openId);
        recordModel.setOptType(Conf.get("shop.red.packet.type:3"));
        recordService.insertSelective(recordModel);

        ParentUserDto parentUserDto = this.getParentOpenId(openId);
        String parentOpenId = parentUserDto.getParentOpenId();
        String performanceRatio = parentUserDto.getPerformanceRatio();
        if(!StringUtil.isEmpty(parentOpenId) && !StringUtil.isEmpty(performanceRatio)){
            BigDecimal bigDecimalRatio = new BigDecimal(performanceRatio).divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_UP);
            BigDecimal parentBonus = bigDecimalMoney.multiply(bigDecimalRatio).setScale(2,BigDecimal.ROUND_DOWN);
            if(parentBonus.compareTo(BigDecimal.ZERO) > 0){
                walletModel.setOpenId(parentOpenId);
                existModel = walletService.selectLimitOne(walletModel);
                if (existModel == null) {
                    walletModel.setId(null);
                    walletModel.setFinance(parentBonus.toString());
                    walletService.insertSelective(walletModel);
                }else {
                    BigDecimal sumFinance = parentBonus.add(new BigDecimal(existModel.getFinance()));
                    existModel.setFinance(sumFinance.toString());
                    walletService.updateByPrimaryKeySelective(existModel);
                }

                recordModel = new ShopReceiveRecordModel();
                recordModel.setAmount(parentBonus.toString());
                recordModel.setOpenId(parentOpenId);
                recordModel.setOptType(Conf.get("shop.red.packet.type:4"));
                recordService.insertSelective(recordModel);
            }
        }

        String topParentOpenId = parentUserDto.getTopParentOpenId();
        String dividendRatio = parentUserDto.getDividendRatio();
        if(!StringUtil.isEmpty(topParentOpenId) && !StringUtil.isEmpty(dividendRatio)){
            BigDecimal bigDecimalRatio = new BigDecimal(dividendRatio).divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_UP);
            BigDecimal topParentBonus = bigDecimalMoney.multiply(bigDecimalRatio).setScale(2,BigDecimal.ROUND_DOWN);
            if(topParentBonus.compareTo(BigDecimal.ZERO) > 0){
                walletModel.setOpenId(topParentOpenId);
                existModel = walletService.selectLimitOne(walletModel);
                if (existModel == null) {
                    walletModel.setId(null);
                    walletModel.setFinance(topParentBonus.toString());
                    walletService.insertSelective(walletModel);
                }else {
                    BigDecimal sumFinance = topParentBonus.add(new BigDecimal(existModel.getFinance()));
                    existModel.setFinance(sumFinance.toString());
                    walletService.updateByPrimaryKeySelective(existModel);
                }

                recordModel = new ShopReceiveRecordModel();
                recordModel.setAmount(topParentBonus.toString());
                recordModel.setOpenId(topParentOpenId);
                recordModel.setOptType(Conf.get("shop.red.packet.type:5"));
                recordService.insertSelective(recordModel);
            }
        }
        return String.valueOf(randomMoney);
    }

    @Override
    public TaskStatusDto getTaskStatus(String openId) {
        return this.mapper.getTaskStatus(openId);
    }

    @Override
    public ParentUserDto getParentOpenId(String openId) {
        return this.mapper.getParentOpenId(openId);
    }

    @Override
    public List<UserDto> getRecommend(String openId) {
        return this.mapper.getRecommend(openId);
    }

    @Override
    public List<UserDto> getTeam(String openId) {
        return this.mapper.getTeam(openId);
    }

    @Override
    public List<TaskDto> listTask(String openId) {
        return this.mapper.listTask(openId);
    }

    @Override
    public void saveMobile(String openId, String mobile) {
        ShopCustomerModel customerModel = new ShopCustomerModel();
        customerModel.setOpenId(openId);
        if (StringUtil.isNotEmpty(mobile)) {
            ShopCustomerModel existModel = this.mapper.selectOne(customerModel);
            existModel.setMobile(mobile);
            this.mapper.updateByPrimaryKeySelective(existModel);
        }
    }

    private void updateRedpacket(String openId, ShopRedpacketDto redpacket) {
        ShopCustomerModel customerModel = new ShopCustomerModel();
        customerModel.setOpenId(openId);
        customerModel.setId(redpacket.getId());
        customerModel.setRedpacketFinance(new BigDecimal(redpacket.getRemainMoney()));
        customerModel.setRedpacketReceive(redpacket.getRemainSize());
        customerModel.setRedpacketAmount(redpacket.getRemainAmount() - 1);
        this.mapper.updateByPrimaryKeySelective(customerModel);
    }

    private double getRandomMoney(ShopRedpacketDto redpacket) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (redpacket.getRemainSize() == 1) {
            redpacket.setRemainSize(redpacket.getRemainSize()-1);
           double leftMoney =  (double) Math.round(Double.parseDouble(redpacket.getRemainMoney()) * 100) / 100;
            redpacket.setRemainMoney(this.sub(redpacket.getRemainMoney(), leftMoney) + "");
            return leftMoney;
        }
        Random r = new Random();
        double min = 1.00;
        double max = this.divide(redpacket.getRemainMoney(), redpacket.getRemainSize() * 0.5);
        double money = 0;
        if(max < min){
            money = max;
            money = Math.floor(money * 100) / 100;
        }else {
            money = this.mul(r.nextDouble(), max);
            money = money <= min ? 1.00: money;
            money = Math.floor(money * 100) / 100;
        }

        redpacket.setRemainSize(redpacket.getRemainSize()-1);
        redpacket.setRemainMoney(this.sub(redpacket.getRemainMoney(), money) + "");
        return money;
    }

    // 提供精确的减法运算。
    private double sub(String value1, Double value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }

    // 提供精确的乘法运算。
    private Double mul(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    private Double divide(String value1, Double value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.divide(b2, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    // 提供精确的减法运算。
    private double add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).doubleValue();
    }

}
