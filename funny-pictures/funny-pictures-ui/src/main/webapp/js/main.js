var mainModule = angular.module("mainModule", ['ngResource', 'ui.bootstrap', 'ngRoute']);

mainModule.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider) {

    $routeProvider
        .when("/", {
            template: "html/mainPage.html",
            controller: 'MainController'
        })
        .when("/home", {
            template: "html/mainPage.html",
            controller: 'MainController'
        })
        .when("/preview", {
            templateUrl: "html/funnyPicturePreview.html",
            controller: 'MainController'
        })
        .otherwise({redirectTo: '/'});
}]);

mainModule.factory('Pictures', function ($resource, $location) {
    return $resource('http://localhost:8080/funny-pictures-rest-api/api/pictures/:id', {}, {'query': {method: 'GET', isArray: false}});
});

mainModule.factory('Funnies', function ($resource, $location) {
    return $resource('http://localhost:8080/funny-pictures-rest-api/api/funnies/:id', {}, {'query': {method: 'GET', isArray: false}});
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

mainModule.controller('MainController', [ '$scope', '$http' , '$location', '$window', 'Pictures' , 'Funnies', function ($scope, $http, $location, $window, Pictures, Funnies) {
    $scope.imagePreview = {};
    $scope.alerts = [];
    $scope.headerText = "";
    $scope.footerText = "";
    $scope.currentPage = 0;
    $scope.totalItems = 11;
    $scope.itemsPerPage = 6;
    $scope.generatedImage = {};

    Pictures.query({
        offset: 0,
        limit: 20
    }, function (data) {
        $scope.pictures = data.entities;
        $scope.totalItems = data.count;
        $scope.imagePreview = data.entities[0];
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

    $scope.changeMainPicture = function (pictureId) {
        Pictures.get({id: pictureId}, function (picture) {
            $scope.imagePreview = picture;
        });
    };

    $scope.createPicture = function () {
        var postObject = new Object();
        postObject.name = $scope.imagePreview.name;
        postObject.header = $scope.headerText;
        postObject.footer = $scope.footerText;
        postObject.template = {};
        postObject.template.id = $scope.imagePreview.id;
        Funnies.save(postObject,
            function (data) {
                $scope.generatedImage = data;
                $scope.alerts.push({type: 'success', msg: "Picture created with id : " + data.id});
             //   $window.location.href = "http://localhost:8080/funny-pictures-ui/html/funnyPicturePreview.html"
                /*$location.path('/preview');*/
            },
            function (error) {
                $scope.alerts.push({type: 'danger', msg: "Can't create picture" + error.statusText + " " + error.status });
            });


    };

} ]);


