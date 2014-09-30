/**
 * Created by rostyslav on 9/29/14.
 */
var funnyControllers = angular.module('funnyControllers', []);

funnyControllers.controller('HomeController', [ '$scope', '$http' , '$location', '$window', 'Pictures' , 'Funnies' , 'SharedProperties', function ($scope, $http, $location, $window, Pictures, Funnies, SharedProperties) {
    $scope.imagePreview = {};
    $scope.alerts = [];
    $scope.headerText = "";
    $scope.footerText = "";
    $scope.currentPage = 1;
    $scope.totalItems = 11;
    $scope.itemsPerPage = 4;

    Pictures.query({
        offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
        limit: $scope.itemsPerPage
    }, function (data) {
        $scope.pictures = data.entities;
        $scope.totalItems = data.count;
        $scope.imagePreview = data.entities[0];
    }, function (error) {
        $scope.totalItems = 0;
        $scope.alerts.push({type: 'danger', msg: error.statusText + " " + error.status });
    });

    $scope.pageChanged = function () {
        Pictures.query({
            offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
            limit: $scope.itemsPerPage
        }, function (data) {
            $scope.pictures = data.entities;
            $scope.totalItems = data.count;
            $scope.imagePreview = data.entities[0];
        }, function (error) {
            $scope.totalItems = 0;
            $scope.alerts.push({type: 'danger', msg: error.statusText + " " + error.status });
        });
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
                SharedProperties.setGeneratedFunny(data);
                $scope.alerts.push({type: 'success', msg: "Picture created with id : " + data.id});
                $location.path('/preview');
            },
            function (error) {
                $scope.alerts.push({type: 'danger', msg: "Can't create picture" + error.statusText + " " + error.status });
            });


    };

} ]);

funnyControllers.controller('PreviewFunnyController', [ '$scope', 'SharedProperties', function ($scope, SharedProperties) {
    $scope.generatedImage = SharedProperties.getGeneratedFunny();
} ]);

funnyControllers.controller('HeaderController', [ '$scope', '$location' , function ($scope, $location) {
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
} ]);

funnyControllers.controller('CreatePictureController', [ '$scope', '$http' , 'FileUpload' , 'SharedProperties', 'Pictures' , '$location', function ($scope, $http, $location, FileUpload, SharedProperties, Pictures) {
    $scope.pictureTitle = "";
    $scope.headerText = "";
    $scope.uploadFile = function () {
        var file = $scope.myFile;
        var uploadUrl = SharedProperties.getApiUrl() + "/content";
        FileUpload.uploadFileToUrl(file, uploadUrl)
            .then(function (data) {
                var pictureObject = {
                    name: $scope.pictureTitle,
                    url: data.path
                };
                Pictures.save(pictureObject,
                    function (data) {
                        $scope.headerText = "File " + data + " uploaded to server!";
                        $location.path('/home');

                    },
                    function (error) {
                        $scope.headerText = "Error" + error.message;
                    }
                );
            });

    };
} ]);

