(function() {
    'use strict';

    angular
        .module('app.core')
        .config(config);

    config
        .$inject = [
            '$routeProvider'
        ];

    function config($routeProvider) {
        $routeProvider
            .when("/home", {
                templateUrl: "html/funnies.html",
                controller: 'FunniesController',
                controllerAs: 'vm'
            })
            .when("/templates", {
                templateUrl: "html/pictures.html",
                controller: 'PicturesController',
                controllerAs: 'vm'
            })
            .when("/createTemplate", {
                templateUrl: "html/createPicture.html",
                controller: 'CreatePictureController',
                controllerAs: 'vm'
            })
            .when("/createFunnyPicture/:pictureId", {
                templateUrl: "html/createFunnyPicture.html",
                controller: 'CreateFunnyController',
                controllerAs: 'vm'
            })
            .when("/preview/:funnyPictureId", {
                templateUrl: "html/funnyPicturePreview.html",
                controller: 'PreviewFunnyController',
                controllerAs: 'vm'
            })
            .when("/contact", {
                templateUrl: "html/contact.html",
                controller: 'ContactController',
                controllerAs: 'vm'
            })
            .when("/authorize", {
                templateUrl: "html/authorize.html",
                controller: 'AuthorizeController',
                controllerAs: 'vm'
            })
            .otherwise({
                redirectTo: '/home'
            });
    }

})();
