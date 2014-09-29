/**
 * Created by rostyslav on 9/29/14.
 */
var funnyPicturesApp = angular.module('mainModule', ['ngResource', 'ui.bootstrap', 'ngRoute', 'funnyControllers']);

funnyPicturesApp.factory('Pictures', function ($resource, $location) {
    return $resource('http://localhost:8080/funny-pictures-rest-api/api/pictures/:id', {}, {'query': {method: 'GET', isArray: false}});
});

funnyPicturesApp.factory('Funnies', function ($resource, $location) {
    return $resource('http://localhost:8080/funny-pictures-rest-api/api/funnies/:id', {}, {'query': {method: 'GET', isArray: false}});
});

funnyPicturesApp.service('sharedProperties', function () {
    var generatedFunny = { };

    return {
        getGeneratedFunny: function () {
            return generatedFunny;
        },
        setGeneratedFunny: function(value) {
            generatedFunny = value;
        }
    };
});

funnyPicturesApp.config(["$routeProvider" , function ($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "html/home.html",
            controller: 'HomeController'
        })
        .when("/preview", {
            templateUrl: "html/funnyPicturePreview.html",
            controller: 'HomeController'
        })
        .otherwise({redirectTo: '/home'});
}]);

funnyPicturesApp.directive('header', function () {
    return {
        restrict: 'E',
        templateUrl: "html/directives/header.html"

    };
});

funnyPicturesApp.directive('footer', function () {
    return {
        restrict: 'E',
        templateUrl: "html/directives/footer.html"

    };
});
