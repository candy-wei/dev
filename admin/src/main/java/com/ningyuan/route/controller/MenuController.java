package com.ningyuan.route.controller;

import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.route.dto.BgMenuDto;
import com.ningyuan.route.model.BgRoleModel;
import com.ningyuan.route.model.BgRouteModel;
import com.ningyuan.route.model.BgUserModel;
import com.ningyuan.route.service.IBgRoleService;
import com.ningyuan.route.service.IBgRouteService;
import com.ningyuan.route.service.IBgUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("menu/")
public class MenuController {
    private static Map<String, List<BgRouteModel>> roleRoutes = new HashMap<>();

    @Autowired
    private IBgUserService userService;

    @Autowired
    private IBgRouteService bgRouteService;

    @Autowired
    private IBgRoleService roleService;

    @PostMapping("/menus")
    @ResponseBody
    public List<BgMenuDto> getMenusByUser() {
        BgUserModel bgUserModel = this.getLoginUser();
        bgUserModel = userService.getUserByUsername(bgUserModel.getUsername());
        List<BgMenuDto> menus = new ArrayList<>();
        Map<Object, BgMenuDto> menuMap = new HashMap<>();

        List<BgRoleModel> roles = roleService.getRolesByUser(bgUserModel.getId());
        for (BgRoleModel role : roles) {
            if (!roleRoutes.containsKey(role.getName())) {
                roleRoutes.put(role.getName(), bgRouteService.getRouteByRole(role.getId()));
            }
            handle(menus, menuMap, roleRoutes.get(role.getName()));
        }

        Iterator<BgMenuDto> bgMenuDtoIterator = menus.iterator();
        while(bgMenuDtoIterator.hasNext()){
            if(CollectionUtils.isEmpty(bgMenuDtoIterator.next().getChildrens())){
                bgMenuDtoIterator.remove();
            }
        }
        return menus;
    }

    @PostMapping("/login/user")
    @ResponseBody
    public BgUserModel getLoginUser() {
        BgUserModel bgUserModel;
        if(Conf.enable("security.authentication.close")) {
            bgUserModel = new BgUserModel();
            bgUserModel.setUsername("admin");
        } else {
            bgUserModel = Context.getCurrentUser(BgUserModel.class);
        }
        return bgUserModel;
    }

    private void handle(List<BgMenuDto> menus, Map<Object, BgMenuDto> menuMap, List<BgRouteModel> routes){
        for (BgRouteModel route : routes) {
            //目录
            if (route.getParentId() == null) {
                if (!menuMap.containsKey(route.getId())) {
                    BgMenuDto bgMenuDto = new BgMenuDto();
                    BeanUtils.copyProperties(route, bgMenuDto);
                    menuMap.put(route.getId(), bgMenuDto);
                    menus.add(bgMenuDto);
                }
                continue;
            }

            //资源
            BgMenuDto bgMenuDto = new BgMenuDto();
            BeanUtils.copyProperties(route, bgMenuDto);

            //先有目录,后添加资源
            if (menuMap.containsKey(route.getParentId()) && route.getIsMenu()) {
                menuMap.get(route.getParentId()).getChildrens().add(bgMenuDto);
            }
        }
    }
}
