(function () {
    app.controller('inviteInfoCtrl', inviteInfoCtrlFn);

    function inviteInfoCtrlFn($scope, inviteInfoService) {
        $scope.customerModel = {};
        inviteInfoService.getInviteList($scope);
    }
}());