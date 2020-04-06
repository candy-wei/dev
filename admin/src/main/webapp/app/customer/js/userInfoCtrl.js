(function () {
    app.controller('userInfoCtrl', userInfoCtrlFn);

    function userInfoCtrlFn($scope, userInfoService) {
        $scope.userInfoModel = {};
        userInfoService.getCustomerList($scope);
        $scope.booleanOptions = [{'value': true, 'text': '是'},{'value': false, 'text': '否'}]

        this.save = function () {
            userInfoService.saveUser($scope.userInfoModel).then(function (response) {
                if ($scope.currentIndex === '') {
                    $scope.pageInfo.list.push(response.data);
                } else {
                    $scope.pageInfo.list[$scope.currentIndex] = response.data;
                }
                console.log('save someThing');
                $('#updateModal').modal('hide');
            });
        }

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