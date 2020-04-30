(function () {
    app.controller('addressInfoCtrl', addressInfoCtrlFn);

    function addressInfoCtrlFn($scope, addressInfoService) {
        $scope.addressModel = {};
        addressInfoService.getAddressList($scope);
    }
}());