"use strict"
app.service('$req', function ($http) {
    this.post = function (uri, data) {
        let params = {};
        //去除空字符串
        for (let key in data)   {
            if(data[key] != null && data[key] != 'null'&& (data[key] !== '' || typeof (data[key]) == 'boolean') ){
                params[key] = data[key];
            }
        }
        return $http.post(path + uri, params);
    }

    this.get = function (uri, data) {
        return $http.get(path + uri, data);
    }
});