/**
 * Created by rostyslav on 9/29/14.
 */
var funnyPicturesApp = angular.module('mainModule', ['ngResource', 'ui.bootstrap', 'ngRoute', 'funnyControllers', 'ngMaterial', 'wu.masonry']);

funnyPicturesApp.factory('Pictures', function($resource, SharedProperties) {
    return $resource(SharedProperties.getApiUrl() + '/pictures/:id', {}, {
        'query': {
            method: 'GET',
            isArray: false
        }
    });
});

funnyPicturesApp.factory('PicturesThumbnails', function($resource, SharedProperties) {
    return $resource(SharedProperties.getApiUrl() + '/picturesthumb/:id', {}, {
        'query': {
            method: 'GET',
            isArray: false
        }
    });
});

funnyPicturesApp.factory('Funnies', function($resource, SharedProperties) {
    return $resource(SharedProperties.getApiUrl() + '/funnies/:id', {}, {
        'query': {
            method: 'GET',
            isArray: false
        }
    });
});

funnyPicturesApp.factory('FunnyPicturesThumbnails', function($resource, SharedProperties) {
    return $resource(SharedProperties.getApiUrl() + '/funniesthumb/:id', {}, {
        'query': {
            method: 'GET',
            isArray: false
        }
    });
});

funnyPicturesApp.factory('Feedback', function($resource, SharedProperties) {
    return $resource(SharedProperties.getApiUrl() + '/feedbacks/:id', {}, {
        'query': {
            method: 'GET',
            isArray: false
        }
    });
});

funnyPicturesApp.service('FileUpload', ['$http', function($http) {
    this.uploadFileToUrl = function(file, uploadUrl) {
        var fd = new FormData();
        fd.append('content', file);
        var request = $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {
                'content': 'file',
                'Content-Type': undefined
            }
        });
        return (request.then(handleSuccess, handleError));
    };

    this.uploadFileUrlToUrl = function(urlToFile, uploadUrl) {
        var request = $http({
            url: uploadUrl,
            method: "POST",
            headers: {
                'content': 'url'
            },
            params: {
                url: urlToFile
            }
        });
        return (request.then(handleSuccess, handleError));
    };

    function handleError(response) {
        return (response.data.message);
    }

    function handleSuccess(response) {
        return (response.data);
    }

}]);

funnyPicturesApp.config(["$routeProvider", function($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "html/home.html",
            controller: 'HomeController'
        })
        .when("/createTemplate", {
            templateUrl: "html/createPicture.html",
            controller: 'CreatePictureController'
        })
        .when("/createFunnyPicture/:templateId", {
            templateUrl: "html/createFunnyPicture.html",
            controller: 'CreateFunnyPictureController'
        })
        .when("/preview/:funnyPictureId", {
            templateUrl: "html/funnyPicturePreview.html",
            controller: 'PreviewFunnyController'
        })
        .when("/funnies", {
            templateUrl: "html/funnies.html",
            controller: 'FunniesController'
        })
        .when("/contact", {
            templateUrl: "html/contact.html",
            controller: 'ContactController'
        })
        .otherwise({
            redirectTo: '/home'
        });
}]);

funnyPicturesApp.directive('fileModel', ['$parse', function($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function() {
                var reader = new FileReader();

                reader.onload = function(e) {
                    var image = $("#image-preview");
                    image.fadeOut('fast', function() {
                        image.attr('src', e.target.result);
                        image.removeClass('hidden');
                        image.fadeIn('fast');
                    });
                };
                reader.readAsDataURL(element[0].files[0]);

                scope.$apply(function() {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

funnyPicturesApp.run(['$route', '$rootScope', '$location', function($route, $rootScope, $location) {
    var original = $location.path;
    $location.path = function(path, reload) {
        if (reload === false) {
            var lastRoute = $route.current;
            var un = $rootScope.$on('$locationChangeSuccess', function() {
                $route.current = lastRoute;
                un();
            });
        }
        return original.apply($location, [path]);
    };
}]);

funnyPicturesApp.directive('header', function() {
    return {
        restrict: 'E',
        templateUrl: "html/directives/header.html"

    };
});

funnyPicturesApp.directive('footer', function() {
    return {
        restrict: 'E',
        templateUrl: "html/directives/footer.html"

    };
});
