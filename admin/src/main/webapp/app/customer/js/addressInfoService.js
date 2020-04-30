(function () {
    app.service('addressInfoService', addressInfoServiceFn);

    function addressInfoServiceFn($req) {
        this.getAddressList = function ($scope) {
            $scope.initList('/customer/address/list', {});
        };
    }
}());