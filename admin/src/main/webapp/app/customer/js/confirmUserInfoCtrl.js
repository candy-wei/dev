(function () {
    app.controller('confirmUserInfoCtrl', confirmUserInfoCtrlFn);

    function confirmUserInfoCtrlFn($scope, confirmUserInfoService) {
        $scope.confirmUserInfoModel = {};
        $scope.settingModel = {};
        confirmUserInfoService.getCustomerList($scope);
        $scope.booleanOptions = [{'value': true, 'text': '是'},{'value': false, 'text': '否'}]
        $scope.vipGrade = [{'value': '1', 'text': '普通会员'},{'value': '2', 'text': '银牌会员'},{'value': '3', 'text': '金牌会员'},
            {'value': '4', 'text': '铂金会员'},{'value': '5', 'text': '钻石会员'},{'value': '6', 'text': '王牌会员'}]

        this.save = function () {
            confirmUserInfoService.saveUser($scope.confirmUserInfoModel).then(function (response) {
                if ($scope.currentIndex === '') {
                    $scope.pageInfo.list.push(response.data);
                } else {
                    $scope.pageInfo.list[$scope.currentIndex] = response.data;
                }
                $('#updateModal').modal('hide');
            });
        }

        this.saveSetting = function () {
            confirmUserInfoService.saveSetting($scope.settingModel).then(function (response) {
                $('#updateSetting').modal('hide');
            });
        }

        this.update = function (index) {
            $scope.currentIndex = index;
            $('.modal-body .form-control').each(function () {
                $scope.confirmUserInfoModel[$(this).attr('name')] = $scope.pageInfo.list[index][$(this).attr('name')];
            })
            $('#modalLabel').text('会员信息修改');
            $('#updateModal').modal();
        }

        this.updateSetting = function () {
            confirmUserInfoService.getSetting().then(function (response) {
                console.log(response)
                $scope.settingModel = response.data
            })
            $('#updateSetting').modal();
        }

        this.addRedpacket = function (index) {
            $scope.currentIndex = index;
            $('.modal-body .form-control').each(function () {
                $scope.confirmUserInfoModel[$(this).attr('name')] = $scope.pageInfo.list[index][$(this).attr('name')];
            })
            $('#addRedpacketModal').modal();
        }

        this.saveRedpacketSum = function (id, addAmount, minusAmount, openId) {
            confirmUserInfoService.saveRedpacketSum(id, addAmount, minusAmount, openId).then(function (response) {
                if (response.data.errorCode === 'SUCCESS') {
                    alert('保存成功')
                    $scope.confirmUserInfoModel = {};
                    confirmUserInfoService.getCustomerList($scope);
                } else {
                    alert('保存失败')
                }
                $('#addRedpacketModal').modal('hide');
            });
        }
    }
}());