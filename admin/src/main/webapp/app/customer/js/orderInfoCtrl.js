(function () {
    app.controller('orderInfoCtrl', orderInfoCtrlFn);

    function orderInfoCtrlFn($scope, orderInfoService) {
        $scope.orderModel = {};
        $scope.statusOpt = [{'value': '1', 'text': '待付款'},{'value': '2', 'text': '待发货'},{'value': '3', 'text': '已发货'},
            {'value': '4', 'text': '已完成'},{'value': '5', 'text': '取消'}]

        $scope.booleanOptions = [{'value': '1', 'text': '是'},{'value': '0', 'text': '否'}]
        orderInfoService.getOrderList($scope);

        this.save = function () {
            orderInfoService.saveOrder($scope.orderModel).then(function (response) {
                if (response.data.errorCode === 'SUCCESS') {
                    alert('修改成功')
                    $scope.pageInfo.list[$scope.currentIndex].status = $scope.orderModel.status;
                }
                $('#updateModal').modal('hide');
            });
        }

        this.update = function (index) {
            $scope.currentIndex = index;
            $('.modal-body .form-control').each(function () {
                $scope.orderModel[$(this).attr('name')] = $scope.pageInfo.list[index][$(this).attr('name')];
            })
            $('#updateModal').modal();
        }
    }
}());