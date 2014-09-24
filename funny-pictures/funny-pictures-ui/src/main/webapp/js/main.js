var mainModule = angular.module("mainModule", ['ngResource', 'ui.bootstrap','ngRoute']);

mainModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/home', {templateUrl:'mainPage.html',controller : "MainController"})
        .otherwise({redirectTo: '/home'});
}]);



mainModule.factory('Pictures', function ($resource,$location) {
    return $resource('http://localhost:8080/funny-pictures-rest-api/api/pictures/:id', {}, {'query': {method: 'GET', isArray: false}});
});

mainModule.directive('header', function () {
    return {
        restrict: 'E',
        templateUrl: "html/directives/header.html"

    };
});

mainModule.directive('footer', function () {
    return {
        restrict: 'E',
        templateUrl: "html/directives/footer.html"

    };
});

mainModule.controller('MainController', [ '$scope', '$http' , '$location', 'Pictures' , function ($scope, $http, $location, Pictures) {
    $scope.imagePreview = "html/test.jpeg";
    $scope.alerts = [];
    $scope.currentPage = 0;
    $scope.totalItems = 11;
    $scope.itemsPerPage = 6;
    $scope.path = $location.absUrl();

    Pictures.query({
        offset: 0,
        limit: 4
    }, function (pictures) {
        $scope.pictures = pictures.entities;
        $scope.test = $scope.pictures;
    }, function (error) {
        $scope.totalItems = 0;
        $scope.alerts.push({type: 'danger', msg: error.statusText + " " + error.status });
    });

    $scope.pageChanged = function () {
        console.log('Page changed to: ' + $scope.currentPage);
    };

    $scope.showPagination = function () {
        return $scope.totalItems != 0;
    };

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.changeMainPicture = function (url) {
        Pictures.get({id : url} , function (picture) {
            $scope.imagePreview = picture.url;
        })
    }

} ]);


