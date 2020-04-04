"use strict";
app.controller('pageCtrl', function ($scope, $req) {
    $scope.queryList = function (data) {
        if (data) {
            $scope.queryModel = data;
        }

        $scope.queryModel.pageSize = $scope.$parent.pageInfo.pageSize;
        $scope.queryModel.pageNum = $scope.$parent.pageInfo.pageNum;
        $req.post($scope.url, $scope.queryModel).then(function (response) {
            $scope.$parent.pageInfo = response.data;
        });
    }

    $scope.initList = function (url, data) {
        $scope.url = url;
        $scope.queryModel = data;
        $scope.pageSizeOption = [10, 20, 30, 40, 50];
        $scope.$parent.pageInfo = {};
        $scope.$parent.pageInfo.pageSize = 10;
        $scope.$parent.pageInfo.pageNum = 1;
        $scope.linkSize = 10;
        $scope.queryList();

        //设置排序
        $(".th-sort").each(function(){
            let field = $(this).attr('field');
            $(this).bind('click', function(){
                if($scope.queryModel.order){
                    if($scope.queryModel.field == field && $scope.queryModel.order == 'asc'){
                        $scope.queryModel.order = 'desc';
                    } else{
                        $scope.queryModel.order = 'asc';
                    }
                } else{
                    $scope.queryModel.order = 'asc';
                }
                $scope.queryModel.field = field;
                $scope.queryList();
            });
        });
    }

    $scope.selectPageSize = function () {
        $scope.queryList();
    }

    $scope.selectPageNum = function (pageNum) {
        $scope.$parent.pageInfo.pageNum = pageNum;
        $scope.queryList();
    }

    $scope.nextPage = function () {
        if ($scope.$parent.pageInfo.hasNextPage) {
            $scope.$parent.pageInfo.pageNum = $scope.$parent.pageInfo.nextPage;
            $scope.queryList();
        }
    }

    $scope.prePage = function () {
        if ($scope.$parent.pageInfo.hasPreviousPage) {
            $scope.$parent.pageInfo.pageNum = $scope.$parent.pageInfo.prePage;
            $scope.queryList();
        }
    }
})

