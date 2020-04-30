"use strict"
app.controller('menuCtrl', function ($scope, $location, menus) {
    $scope.cssPath = appPath;
    $scope.cssVersion = version;
    $scope.path = path;
    $scope.initMenu = function () {
        $scope.wHeigth = document.documentElement.clientHeight - $("#nav-header").height() - 1;
        menus.getMenus().then(function (response) {
                $scope.menus = menus.sortMenu(response.data);
                $scope.mainActive = 0;
                $scope.subActive = 0;
                $scope.menuShow($scope.mainActive);
            }
        )

        menus.getUser().then(function (response) {
                $scope.user = response.data;
            }
        )
    }

    $scope.menuShow = function (menuIndex) {
        $scope.menus[$scope.mainActive].active = "main-menu";
        $scope.menus[menuIndex].active = "main-active";
        $scope.menuName =  $scope.menus[menuIndex].name;
        $scope.childrens = $scope.menus[menuIndex].childrens;
        $scope.subMenuShow(menuIndex, 0);
    };

    $scope.subMenuShow = function (menuIndex, subIndex) {
        if ($scope.menus[$scope.mainActive].childrens.length > 0) {
            $scope.menus[$scope.mainActive].childrens[$scope.subActive].active = "sub-menu";
        }

        if ($scope.menus[menuIndex].childrens.length > 0) {
            $scope.menus[menuIndex].childrens[subIndex].active = "sub-active";
        }

        $scope.mainActive = menuIndex;
        $scope.subActive = subIndex;
        $scope.subName =  $scope.menus[$scope.mainActive].childrens[$scope.subActive].name;
        if ($scope.menus[$scope.mainActive].childrens.length > 0) {
            $location.path($scope.menus[$scope.mainActive].childrens[$scope.subActive].url);
        } else{
            $location.path("/");
        }
    }

    $scope.logout = function(){
        $("#logout").submit();
    }
})

