/**
 * Created by rostyslav on 9/29/14.
 */
var funnyPicturesApp = angular.module('mainModule', ['ngResource', 'ui.bootstrap', 'ngRoute', 'funnyControllers']);

funnyPicturesApp.factory('Pictures', function ($resource, SharedProperties) {
        return $resource(SharedProperties.getApiUrl() +'/pictures/:id',{},{'query': {method: 'GET', isArray: false}});
    }
)
;

funnyPicturesApp.factory('Funnies', function ($resource, SharedProperties) {
    return $resource(SharedProperties.getApiUrl() +'/funnies/:id', {}, {'query': {method: 'GET', isArray: false}});
});

funnyPicturesApp.service('SharedProperties', function () {
    var generatedFunny = { };
    var apiUrl = "http://localhost:8080/funny-pictures-rest-api/api";
    return {
        getGeneratedFunny: function () {
            return generatedFunny;
        },
        setGeneratedFunny: function (value) {
            generatedFunny = value;
        },
        getApiUrl: function () {
            return apiUrl;
        }
    };
});

funnyPicturesApp.service('FileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function (file, uploadUrl) {
        var fd = new FormData();
        fd.append('content', file);
        var request = $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
        return ( request.then( handleSuccess, handleError ) )
    };

    function handleError( response ) {
        return(  response.data.message );
    }

    function handleSuccess( response ) {
        return( response.data );
    }

}]);

funnyPicturesApp.config(["$routeProvider" , function ($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "html/home.html",
            controller: 'HomeController'
        })
        .when("/create", {
            templateUrl: "html/createPicture.html",
            controller: 'CreatePictureController'
        })
        .when("/preview", {
            templateUrl: "html/funnyPicturePreview.html",
            controller: 'PreviewFunnyController'
        })
        .otherwise({redirectTo: '/home'});
}]);

funnyPicturesApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
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
