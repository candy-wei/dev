"use strict"
app.service('menus', function($req) {
    let menuService = this;

    this.sortBySeq = function (a, b){
        return b.seq - a.seq;
    }

    this.sortMenu = function(menus){
        for(let i = 0; i < menus.length; i++){
            menus[i].childrens = menus[i].childrens.sort(menuService.sortBySeq);
        }
        return menus.sort(menuService.sortBySeq);
    }

    this.getMenus  = function () {
        return $req.post("/menu/menus");
    }

    this.getUser  = function () {
        return $req.post("/menu/login/user");
    }
});