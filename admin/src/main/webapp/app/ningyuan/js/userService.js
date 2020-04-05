(function () {
    app.service('userService', userServiceFn);

    function userServiceFn($req) {

        this.getCustomerList = function ($scope) {
            $scope.initList('/user/list', {});
        };

        this.saveUser = function (data) {
            return $req.post('/user/update', data);
        }
    }
}());