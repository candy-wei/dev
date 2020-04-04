"use strict"
app.config(["$stateProvider", "$urlRouterProvider", "$routesDataProvider", routeFn]);

function routeFn($stateProvider, $urlRouterProvider, $routesDataProvider) {
    let routes = $routesDataProvider.getRoutes();
    $urlRouterProvider.otherwise(routes[0].url);
    routes.forEach(item => {
        if (typeof (item.resolve) == 'string') {
            item.resolve = JSON.parse(item.resolve);
        }

        for (let i = 0; i < item.resolve.length; i++) {
            item.resolve[i] = appPath + item.resolve[i] + "?" + version;
        }

        $stateProvider
            .state(item.state, {
                url: item.url,
                templateUrl: appPath + item.view + ".html",
                controller: item.ctrl + "Ctrl",
                controllerAs: item.ctrl,
                resolve: {
                    deps: ["$ocLazyLoad", function ($ocLazyLoad) {
                        return $ocLazyLoad.load(item.resolve);
                    }]
                }
            })
    })

};