(function () {
    app.controller('orderInfoCtrl', orderInfoCtrlFn);

    function orderInfoCtrlFn($scope, orderInfoService) {
        $scope.orderModel = {};
        $scope.booleanOptions = [{'value': '1', 'text': '是'},{'value': '0', 'text': '否'}]
        orderInfoService.getOrderList($scope);
    }
}());