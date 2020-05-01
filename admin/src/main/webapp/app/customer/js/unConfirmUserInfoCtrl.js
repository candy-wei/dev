(function () {
    app.controller('unConfirmUserInfoCtrl', unConfirmUserInfoCtrlFn);

    function unConfirmUserInfoCtrlFn($scope, unConfirmUserInfoService) {
        $scope.unConfirmUserInfoModel = {};
        unConfirmUserInfoService.getUnconfirmList($scope);

        $scope.booleanOptions = [{'value': true, 'text': '是'},{'value': false, 'text': '否'}]
        $scope.vipGrade = [{'value': '1', 'text': '普通会员'},{'value': '2', 'text': '银牌会员'},{'value': '3', 'text': '金牌会员'},
            {'value': '4', 'text': '铂金会员'},{'value': '5', 'text': '钻石会员'},{'value': '6', 'text': '王牌会员'}]

        this.save = function (id) {
            unConfirmUserInfoService.updateConfirm(id).then(function (response) {
                if (response.data.errorCode === 'SUCCESS') {
                    alert('修改成功')
                } else {
                    alert('修改失败')
                }
                unConfirmUserInfoService.getUnconfirmList($scope);
                $('#confirmModal').modal('hide');
            });
        }

        this.update = function (index) {
            $scope.currentIndex = index;
            $('.modal-body .form-control').each(function () {
                $scope.unConfirmUserInfoModel[$(this).attr('name')] = $scope.pageInfo.list[index][$(this).attr('name')];
            })
            $('#confirmModal').modal();
        }
    }
}());