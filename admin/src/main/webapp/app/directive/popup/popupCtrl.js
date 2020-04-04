"use strict";
app.controller("popopCtrl", function ($scope, $req) {
    $scope.popupInfo = {
        title: "提示",
        content: "提示内容",
        btnCloseText: "取消",
        btnRightStyle: "btn-primary",
        btnRightText: "确定"
    };
    $scope.popupClick = function () {
        alert("点击确定");
    }
});