(function () {
    app.service('unConfirmUserInfoService', unConfirmUserInfoServiceFn);

    function unConfirmUserInfoServiceFn($req) {

        this.getUnconfirmList = function ($scope) {
            $scope.initList('/user/list/unConfirm', {});
        };

        this.updateConfirm = function (id) {
            let param = {"id": id}
            return $req.post('/user/confirm/update', param);
        }
    }
}());