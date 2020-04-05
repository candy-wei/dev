(function () {
    app.service('userInfoService', userInfoServiceFn);

    function userInfoServiceFn($req) {

        this.getCustomerList = function ($scope) {
            $scope.initList('/user/list', {});
        };

        this.saveUser = function (data) {
            return $req.post('/user/update', data);
        }
    }
}());