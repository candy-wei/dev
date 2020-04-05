(function () {
    app.controller('userCtrl', userCtrlFn);

    function userCtrlFn($scope, userService) {
        $scope.userInfoModel = {};
        userService.getCustomerList($scope);
        $scope.booleanOptions = [{'value': true, 'text': '是'},{'value': false, 'text': '否'}]

        userService.saveUser($scope.userInfoModel).then(function (response) {
            /*if ($scope.currentIndex === '') {
                $scope.pageInfo.list.push(response.data);
            } else {
                $scope.pageInfo.list[$scope.currentIndex] = response.data;
            }*/
            console.log('save someThing');
            $('#updateModal').modal('hide');
        });

        this.update = function (index) {
            $scope.currentIndex = index;
            $('.modal-body .form-control').each(function () {
                $scope.userInfoModel[$(this).attr('name')] = $scope.pageInfo.list[index][$(this).attr('name')];
            })
            $('#modalLabel').text('会员信息修改');
            $('#updateModal').modal();
        }
    }
}());