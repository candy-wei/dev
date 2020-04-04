"use strict";
app.directive("popup", function () {
    return {
        restrict:"EACM",
        transclude:true,
        scope: false,
        templateUrl: appPath + '/directive/popup/popup.html',
    }
});