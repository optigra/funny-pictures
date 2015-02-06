/**
 * Created by rostyslav on 9/29/14.
 */
var funnyControllers = angular.module('funnyControllers', ['pascalprecht.translate']);

funnyControllers.controller('HomeController', [
    '$scope',
    '$location',
    '$mdToast',
    'PicturesThumbnails',
    'Funnies',
    'SharedProperties',
    function ($scope, $location, $mdToast, PicturesThumbnails, Funnies,
              SharedProperties) {
        $scope.headerText = "";
        $scope.footerText = "";
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.itemsPerPage = 15;
        $scope.thumbnailType = "MEDIUM";

        PicturesThumbnails.query({
            offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
            limit: $scope.itemsPerPage,
            thumbnailType: $scope.thumbnailType
        }, function (data) {
            $scope.pictureThumbnails = data.entities;
            $scope.totalItems = data.count;
        }, function (error) {
            $scope.totalItems = 0;
            $mdToast.show(
                $mdToast.simple()
                    .content('Error ' + error.message)
                    .position('bottom left')
                    .hideDelay(5000)
            );
        });

        $scope.pageChanged = function () {
            PicturesThumbnails.query({
                offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                limit: $scope.itemsPerPage,
                thumbnailType: $scope.thumbnailType
            }, function (data) {
                $scope.pictureThumbnails = data.entities;
                $scope.totalItems = data.count;
            }, function (error) {
                $scope.totalItems = 0;
                $mdToast.show(
                    $mdToast.simple()
                        .content('Error ' + error.message)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        };

        $scope.showPagination = function () {
            return $scope.totalItems > $scope.itemsPerPage;
        };

        $scope.createFunnyPicture = function (templateId) {
            $location.path('/createFunnyPicture/' + templateId);
        };

        $scope.go = function (path) {
            $location.path(path);
        };
    }
]);

funnyControllers.controller('HeaderController', ['$scope', '$location','$translate',
    function ($scope, $location, $translate) {
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };
        $scope.go = function (path) {
            $location.path(path);
        };
    }
]);

funnyControllers.controller('FunniesController', ['$scope', '$mdToast',
        'FunnyPicturesThumbnails',
        function ($scope, $mdToast, FunnyPicturesThumbnails) {
            $scope.funniesThumbnails = {};
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.itemsPerPage = 15;
        $scope.thumbnailType = "MEDIUM";

        FunnyPicturesThumbnails.query({
            offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
            limit: $scope.itemsPerPage,
            thumbnailType: $scope.thumbnailType
        }, function (data) {
            $scope.funniesThumbnails = data.entities;
            $scope.totalItems = data.count;
        }, function (error) {
            $scope.totalItems = 0;
            $mdToast.show(
                $mdToast.simple()
                    .content('Error ' + error.message)
                    .position('bottom left')
                    .hideDelay(5000)
            );
        });

        $scope.pageChanged = function () {
            FunnyPicturesThumbnails.query({
                offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                limit: $scope.itemsPerPage,
                thumbnailType: $scope.thumbnailType
            }, function (data) {
                $scope.funniesThumbnails = data.entities;
                $scope.totalItems = data.count;
            }, function (error) {
                $scope.totalItems = 0;
                $mdToast.show(
                    $mdToast.simple()
                        .content('Error ' + error.message)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        };

        $scope.showPagination = function () {
            return $scope.totalItems > $scope.itemsPerPage;
        };
    }
]);

funnyControllers.controller('PreviewFunnyController', [
    '$scope',
    '$routeParams',
    '$http',
    '$location',
    '$window',
    '$mdToast',
    'SharedProperties',
    'Funnies',
    function ($scope, $routeParams, $http, $location, $window, $mdToast, SharedProperties, Funnies) {
        $scope.funnyPicture = {};
        $scope.funniesByTemplate = {};
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.itemsPerPage = 6;
        $scope.thumbnailType = "MEDIUM";
        $scope.currentUrl = $location.absUrl().split('#')[0] + '#/preview/';
        $scope.currentLocation = $scope.currentUrl + $routeParams.funnyPictureId;

        Funnies.get({
            id: $routeParams.funnyPictureId
        }, function (funnyPicture) {
            $scope.funnyPicture = funnyPicture;
            $http({
                url: SharedProperties.getApiUrl() + "/pictures/" + $scope.funnyPicture.template.id + "/funniesThumb",
                method: "GET",
                params: {
                    offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                    limit: $scope.itemsPerPage,
                    thumbnailType: $scope.thumbnailType
                }
            }).success(function (data, status) {
                $scope.funniesByTemplate = data.entities;
                $scope.totalItems = data.count;
            }).error(function (error) {
                $mdToast.show(
                    $mdToast.simple()
                        .content(error.statusText + ' ' + error.status)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        }, function (error) {
            $mdToast.show(
                $mdToast.simple()
                    .content('Error ' + error.message)
                    .position('bottom left')
                    .hideDelay(5000)
            );
        });

        $scope.pageChanged = function () {
            $http({
                url: SharedProperties.getApiUrl() + "/pictures/" + $scope.funnyPicture.template.id + "/funniesThumb",
                method: "GET",
                params: {
                    offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                    limit: $scope.itemsPerPage,
                    thumbnailType: $scope.thumbnailType
                }
            }).success(function (data, status) {
                $scope.funniesByTemplate = data.entities;
                $scope.totalItems = data.count;
            }).error(function (error) {
                $mdToast.show(
                    $mdToast.simple()
                        .content(error.statusText + ' ' + error.status)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        };

        $scope.swapFunnyPicture = function (funnyThumbnail) {
            $location.path('/preview/' + funnyThumbnail.funnyPictureId, false);
            $scope.currentLocation = $scope.currentUrl + funnyThumbnail.funnyPictureId;
            Funnies.get({
                id: funnyThumbnail.funnyPictureId
            }, function (funnyPicture) {
                $scope.funnyPicture = funnyPicture;
            }, function (error) {
                $mdToast.show(
                    $mdToast.simple()
                        .content('Error ' + error.message)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        };

        $scope.shareSocial = function (baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent($scope.currentLocation);
            event.preventDefault();
            $window.open(url, "_blank", "width=" + width + ",height=" + height);
        };

        $scope.showPagination = function () {
            return $scope.totalItems > $scope.itemsPerPage;
        };
    }
]);

funnyControllers.controller('CreatePictureController', [
    '$scope',
    '$location',
    '$window',
    '$mdToast',
    'FileUpload',
    'SharedProperties',
    'Pictures',
    function ($scope, $location, $window, $mdToast, FileUpload, SharedProperties,
              Pictures) {
        $scope.pictureTitle = "";
        $scope.pictureUrl = "";
        $scope.uploadFile = function () {
            var uploadUrl = SharedProperties.getApiUrl() + "/content";
            var file = $scope.myFile;
            var urlToFile = $scope.pictureUrl;
            if (file || urlToFile) {
                var promiseFile = {};
                if (file) {
                    promiseFile = FileUpload.uploadFileToUrl(file, uploadUrl);
                } else if (urlToFile) {
                    promiseFile = FileUpload.uploadFileUrlToUrl(urlToFile, uploadUrl);
                }
                promiseFile.then(
                    function (data) {
                        var pictureObject = {
                            name: $scope.pictureTitle,
                            url: data.path
                        };
                        Pictures.save(pictureObject, function (data) {
                                $mdToast.show(
                                    $mdToast.simple()
                                        .content('File ' + data.name + ' uploaded to server!')
                                        .position('bottom left')
                                        .hideDelay(5000)
                                );
                                $location.path('/home');
                            },
                            function (error) {
                                $mdToast.show(
                                    $mdToast.simple()
                                        .content('Error ' + error.message)
                                        .position('bottom left')
                                        .hideDelay(5000)
                                );
                            }
                        );
                    }
                );
            } else {
                $mdToast.show(
                    $mdToast.simple()
                        .content('Please select the image!')
                        .position('bottom left')
                        .hideDelay(5000)
                );
            }
        };
        $scope.isButtonDisabled = function () {
            return !$scope.templateInputForm.$valid;
        };
        $scope.callUpload = function () {
            $('#upload').click();
        };
    }
]);

funnyControllers.controller('CreateFunnyPictureController', [
    '$scope',
    '$routeParams',
    '$window',
    '$http',
    '$mdDialog',
    '$mdToast',
    'SharedProperties',
    'Pictures',
    'Funnies',
    function ($scope, $routeParams, $window, $http, $mdDialog, $mdToast, SharedProperties, Pictures,
              Funnies) {
        $scope.template = {};
        $scope.funnyPicture = {};
        $scope.funniesByTemplate = {};
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.itemsPerPage = 6;
        $scope.thumbnailType = "MEDIUM";
        $scope.progress = false;
        $scope.loaded = false;


        Pictures.get({
            id: $routeParams.templateId
        }, function (picture) {
            $scope.template = picture;
            $http({
                url: SharedProperties.getApiUrl() + "/pictures/" + $scope.template.id + "/funniesThumb",
                method: "GET",
                params: {
                    offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                    limit: $scope.itemsPerPage,
                    thumbnailType: $scope.thumbnailType
                }
            }).success(function (data, status) {
                $scope.funniesByTemplate = data.entities;
                $scope.totalItems = data.count;
            }).error(function (error) {
                $mdToast.show(
                    $mdToast.simple()
                        .content(error.statusText + ' ' + error.status)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        });

        $scope.pageChanged = function () {
            $http({
                url: SharedProperties.getApiUrl() + "/pictures/" + $scope.template.id + "/funniesThumb",
                method: "GET",
                params: {
                    offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                    limit: $scope.itemsPerPage,
                    thumbnailType: $scope.thumbnailType
                }
            }).success(function (data, status) {
                $scope.funniesByTemplate = data.entities;
                $scope.totalItems = data.count;
            }).error(function (error) {
                $mdToast.show(
                    $mdToast.simple()
                        .content(error.statusText + ' ' + error.status)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        };

        $scope.createFunnyPicture = function () {
            $scope.progress = true;
            var postObject = new Object();
            postObject.name = $scope.template.name;
            postObject.header = $scope.headerText;
            postObject.footer = $scope.footerText;
            postObject.template = {};
            postObject.template.id = $scope.template.id;
            Funnies.save(postObject, function (data) {
                $scope.funnyPicture = data;
                $scope.pageChanged();
                $scope.progress = false;
                $scope.loaded = true;
            }, function (error) {
                $scope.progress = false;
                $mdToast.show(
                    $mdToast.simple()
                        .content("Can't create picture " + error.statusText + ' ' + error.status)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        };

        $scope.cancel = function () {
            $http({
                url: SharedProperties.getApiUrl() + "/funnies/" + $scope.funnyPicture.id,
                method: "DELETE"
            }).success(function (status) {
                $scope.pageChanged();
                $scope.loaded = false;
                $scope.funnyPicture = {};
            }).error(function (error) {
                $mdToast.show(
                    $mdToast.simple()
                        .content('Funny picture has not been deleted ' + error.statusText + ' ' + error.status)
                        .position('bottom left')
                        .hideDelay(5000)
                );
            });
        }

        $scope.createNew = function () {
            $scope.headerText = '';
            $scope.footerText = '';
            $scope.loaded = false;
            $scope.funnyPicture = {};
        }

        $scope.isButtonDisabled = function () {
            return !$scope.funnyPictureText.$valid;
        }

        $scope.showPagination = function () {
            return $scope.totalItems > $scope.itemsPerPage;
        };

    }
]);

funnyControllers.controller('ContactController', [
    '$scope',
    '$mdToast',
    'Feedback',
    function ($scope, $mdToast, Feedback) {
        $scope.feedback = {};
        $scope.sendFeedback = function () {
            Feedback.save($scope.feedback,
                function (data, status) {
                    $mdToast.show(
                        $mdToast.simple()
                            .content('Thank you ' + $scope.feedback.name + ', your feedback was sent.')
                            .position('bottom left')
                            .hideDelay(5000)
                    );
                },
                function (error) {
                    $mdToast.show(
                        $mdToast.simple()
                            .content("Feedback can't sent " + error.statusText + ' ' + error.status)
                            .position('bottom left')
                            .hideDelay(5000)
                    );
                }
            );
        }
        $scope.isButtonDisabled = function () {
            return !$scope.contactForm.$valid;
        };
    }
]);
