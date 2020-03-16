package com.ningyuan.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ningyuan.base.BaseController;
import com.ningyuan.core.Context;
import com.ningyuan.core.CustomizedPropertyConfigurer;
import com.ningyuan.system.dto.SysParamsQueryDto;
import com.ningyuan.system.model.SysParamsModel;
import com.ningyuan.system.service.ISysParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("rest/sys/params")
public class  SysParamsController extends BaseController {

    @Autowired
    private ISysParamsService sysParamsService;

    @PostMapping("list")
    @ResponseBody
    public PageInfo<SysParamsModel> getParamss(SysParamsQueryDto sysParamsQueryDto){
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(sysParamsService.selectByExample(sysParamsQueryDto.toExample()));
    }

    @PostMapping("add")
    @ResponseBody
    public SysParamsModel addParams(SysParamsModel paramsModel){
        if(StringUtils.isEmpty(paramsModel.getId())){
            sysParamsService.insertSelective(paramsModel);
            return paramsModel;
        }
        sysParamsService.updateByPrimaryKeySelective(paramsModel);
        this.reload();
        return paramsModel;
    }

    @PostMapping("delete")
    @ResponseBody
    public void deleteParams(SysParamsModel paramsModel){
        sysParamsService.delete(paramsModel);
    }

    @PostMapping("update")
    @ResponseBody
    public SysParamsModel updateParams(SysParamsModel paramsModel){
        sysParamsService.updateByPrimaryKeySelective(paramsModel);
        return paramsModel;
    }

    @PostMapping("delete/{id}")
    @ResponseBody
    public void deleteParams(@PathVariable Long id){
        sysParamsService.deleteByPrimaryKey(id);
    }

    @RequestMapping("config/{type}")
    @ResponseBody
    public Map<String, String> getConf(@PathVariable String type){
        SysParamsModel paramsModel = new SysParamsModel();
        paramsModel.setParamType(type);
        List<SysParamsModel> params = sysParamsService.select(paramsModel);
        Map<String, String> map = new HashMap<>();
        params.forEach(item->{
            map.put(item.getParamKey(), item.getParamValue());
        })  ;
        return map;
    }

    private void reload(){
        try{
            CustomizedPropertyConfigurer.loadPropsFromDb();
//            RESTUtils.get(Conf.get("promote.domain.path") + "/rest/reload/conf", String.class);
        }catch (Exception e){
        }
    }
}
