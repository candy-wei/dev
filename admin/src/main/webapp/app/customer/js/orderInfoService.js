(function () {
    app.service('orderInfoService', orderInfoServiceFn);

    function orderInfoServiceFn($req) {
        this.getOrderList = function ($scope) {
            $scope.initList('/customer/order/list', {});
        };
    }
}());