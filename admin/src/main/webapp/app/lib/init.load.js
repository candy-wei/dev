function loadCss(cssList){
    let head = document.getElementsByTagName("head").item(0);
    // 动态加载 css
    cssList.forEach(item => {
        let css = document.createElement('link');
        css.href = appPath + item + "?" + version;
        css.rel = "stylesheet";
        head.appendChild(css);
    })
}

function asyncLoadJs(asyncScriptList){
    let head = document.getElementsByTagName("head").item(0);
    asyncScriptList.forEach(item => {
        let script = document.createElement('script');
        script.src = appPath + item + "?" + version;
        head.appendChild(script);
    })
}

function loadJs(scriptList, success){
    let head = document.getElementsByTagName("head").item(0);
    // 动态加载 js
    let scripts = [];
    let next = 0;
    for (let i = 0; i < scriptList.length; i++) {
        let script = document.createElement('script');
        script.src = appPath + scriptList[i] + "?" + version;;
        script.onload = function () {
            next += 1;
            if (next < scripts.length) {
                head.appendChild(scripts[next]);
            }
            if(next == scripts.length){
                if(success){
                   success();
                }
            }
        };
        scripts.push(script)
    }
    head.appendChild(scripts[0]);
}

(function () {
    let cssList = [
        "/lib/css/bootstrap.min.css",
        "/lib/css/bootstrap-datetimepicker.min.css",
        "/menu/css/menu.css",
        "/css/table.css",
    ];

    let scriptList = [
        "/lib/js/jquery-3.3.1.min.js",
        "/lib/js/angular.min.js",
        "/lib/js/angular-ui-router.min.js",
        "/lib/js/angular-sanitize.min.js",
        "/lib/js/ocLazyLoad.min.js",
        "/lib/js/bootstrap.min.js",
        "/lib/js/bootstrap-datetimepicker.min.js",
        "/lib/js/bootstrap-datetimepicker.zh-CN.js",
        "/lib/app.config.js",
        "/lib/route.config.js",
        "/lib/routesProvider.js",
    ];

    let asyncScriptList = [
        "/lib/reqService.js",
        "/lib/init.config.js",
        "/menu/js/menuService.js",
        "/menu/js/menuCtrl.js",
        "/directive/page/pageDirective.js",
        "/directive/page/pageCtrl.js",
        "/directive/popup/popupDirective.js",
        "/directive/popup/popupCtrl.js",]

    loadCss(cssList);
    loadJs(scriptList, function () {
        asyncLoadJs(asyncScriptList);
    });
})();

