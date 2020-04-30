(function () {
    app.service('inviteInfoService', inviteInfoServiceFn);

    function inviteInfoServiceFn($req) {
        this.getInviteList = function ($scope) {
            $scope.initList('/customer/invite/list', {});
        };
    }
}());