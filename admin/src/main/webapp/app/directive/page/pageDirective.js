app.directive("pager",function(){
    return{
        restrict:"EACM",
        transclude:true,
        scope: false,
        templateUrl: appPath + '/directive/page/pager.html',
    }
});