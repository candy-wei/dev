if ('undefined' == typeof (appConf)) {
    appConf = {};
}

(function () {
    $.ajax({
        url: path + "/rest/sys/params/config/app",
        async: false,
        data: {},
        success: function (response) {
            if (typeof (response) == 'string' && response.indexOf("登录") != -1) {
                //还没有登录
                location.href = path + "/login.jsp";
                return;
            }
            for(var key in response){
                if(!appConf[key]){
                    appConf[key] = response[key];
                }
            }
        }
    });
})();