(function () {
    app.service('orderInfoService', orderInfoServiceFn);

    function orderInfoServiceFn($req) {
        this.getOrderList = function ($scope) {
            $scope.initList('/customer/order/list', {});
        }

        this.saveOrder = function (data) {
            return $req.post('/customer/order/update', data);
        }
    }
}());