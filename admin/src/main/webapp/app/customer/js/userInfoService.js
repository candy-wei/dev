(function () {
    app.service('userInfoService', userInfoServiceFn);

    function userInfoServiceFn($req) {

        this.getCustomerList = function ($scope) {
            $scope.initList('/user/list', {});
        };

        this.saveUser = function (data) {
            return $req.post('/user/update', data);
        }

        this.saveSetting = function (data) {
            return $req.post('/user/updateSetting', data);
        }

        this.getSetting = function () {
            return $req.get('/user/getSetting');
        }

        this.saveRedpacketSum = function (id, redpacketAmount, openId) {
            let param = {
                "id": id,
                "redpacketAmount": redpacketAmount,
                "openId": openId
            }
            return $req.post('/user/updateRedpacketSum', param);
        }
    }
}());