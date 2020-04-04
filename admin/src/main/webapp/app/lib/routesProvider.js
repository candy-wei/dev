"use strict"
app.provider('$routesData', function () {
    this.getRoutes = function () {
        let routes = [];
        let data = {};
        $.ajax({ url: path + "/menu/states",
            type:"POST",
            async:false,
            data: data,
            success: function(response){
                routes = response;
            }});
        return routes;
    }

    this.$get = function () {
    }
});