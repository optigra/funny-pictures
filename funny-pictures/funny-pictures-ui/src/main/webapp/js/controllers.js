var funnyControllers = angular.module("funnyControllers", []);

funnyControllers.controller("HomeController", [ "$scope", "$location", "$mdToast", "PicturesThumbnails", "Funnies", "SharedProperties", function($scope, $location, $mdToast, PicturesThumbnails, Funnies, SharedProperties) {
    $scope.headerText = "", $scope.footerText = "", $scope.currentPage = 1, $scope.totalItems = 0, 
    $scope.itemsPerPage = 8, $scope.thumbnailType = "MEDIUM", PicturesThumbnails.query({
        offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
        limit: $scope.itemsPerPage,
        thumbnailType: $scope.thumbnailType
    }, function(data) {
        $scope.pictureThumbnails = data.entities, $scope.totalItems = data.count;
    }, function(error) {
        $scope.totalItems = 0, $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
    }), $scope.pageChanged = function() {
        PicturesThumbnails.query({
            offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
            limit: $scope.itemsPerPage,
            thumbnailType: $scope.thumbnailType
        }, function(data) {
            $scope.pictureThumbnails = data.entities, $scope.totalItems = data.count;
        }, function(error) {
            $scope.totalItems = 0, $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
        });
    }, $scope.showPagination = function() {
        return $scope.totalItems > $scope.itemsPerPage;
    }, $scope.createFunnyPicture = function(templateId) {
        SharedProperties.setTemplateId(templateId), $location.path("/createFunnyPicture");
    }, $scope.go = function(path) {
        $location.path(path);
    };
} ]), funnyControllers.controller("PreviewFunnyController", [ "$scope", "SharedProperties", function($scope, SharedProperties) {
    $scope.generatedImage = SharedProperties.getGeneratedFunny();
} ]), funnyControllers.controller("HeaderController", [ "$scope", "$location", function($scope, $location) {
    $scope.isActive = function(viewLocation) {
        return viewLocation === $location.path();
    }, $scope.go = function(path) {
        $location.path(path);
    };
} ]), funnyControllers.controller("FunniesController", [ "$scope", "$mdDialog", "$mdToast", "FunnyPicturesThumbnails", function($scope, $mdDialog, $mdToast, FunnyPicturesThumbnails) {
    $scope.funnies = {}, $scope.currentPage = 1, $scope.totalItems = 0, $scope.itemsPerPage = 8, 
    $scope.thumbnailType = "MEDIUM", $scope.modalOpen = function(funnyThumbnail) {
        $mdDialog.show({
            controller: "FunnyPreviewModalController",
            templateUrl: "html/modal/funnyPreviewModal.tmpl.html",
            resolve: {
                funnyThumbnail: function() {
                    return funnyThumbnail;
                }
            }
        });
    }, FunnyPicturesThumbnails.query({
        offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
        limit: $scope.itemsPerPage,
        thumbnailType: $scope.thumbnailType
    }, function(data) {
        $scope.funniesThumbnails = data.entities, $scope.totalItems = data.count;
    }, function(error) {
        $scope.totalItems = 0, $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
    }), $scope.pageChanged = function() {
        FunnyPicturesThumbnails.query({
            offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
            limit: $scope.itemsPerPage,
            thumbnailType: $scope.thumbnailType
        }, function(data) {
            $scope.funniesThumbnails = data.entities, $scope.totalItems = data.count;
        }, function(error) {
            $scope.totalItems = 0, $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
        });
    }, $scope.showPagination = function() {
        return $scope.totalItems > $scope.itemsPerPage;
    };
} ]), funnyControllers.controller("FunnyPreviewModalController", [ "$scope", "$window", "$mdDialog", "funnyThumbnail", "Funnies", function($scope, $window, $mdDialog, funnyThumbnail, Funnies) {
    var funnyPictureId = funnyThumbnail.funnyPictureId;
    Funnies.get({
        id: funnyPictureId
    }, function(funnyPicture) {
        $scope.funnyPicture = funnyPicture;
    }), $scope.cancel = function() {
        $mdDialog.cancel();
    }, $scope.fullSize = function() {
        $window.open($scope.funnyPicture.url);
    };
} ]), funnyControllers.controller("CreatePictureController", [ "$scope", "$location", "$window", "$mdToast", "FileUpload", "SharedProperties", "Pictures", function($scope, $location, $window, $mdToast, FileUpload, SharedProperties, Pictures) {
    $scope.pictureTitle = "", $scope.uploadFile = function() {
        var uploadUrl = SharedProperties.getApiUrl() + "/content", file = $scope.myFile;
        file ? FileUpload.uploadFileToUrl(file, uploadUrl).then(function(data) {
            var pictureObject = {
                name: $scope.pictureTitle,
                url: data.path
            };
            Pictures.save(pictureObject, function(data) {
                $mdToast.show($mdToast.simple().content("File " + data.name + " uploaded to server!").position("bottom left").hideDelay(5e3)), 
                $location.path("/home");
            }, function(error) {
                $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
            });
        }) : $mdToast.show($mdToast.simple().content("Please select the image!").position("bottom left").hideDelay(5e3));
    }, $scope.isButtonDisabled = function() {
        return !$scope.templateInputForm.$valid;
    }, $scope.callUpload = function() {
        $("#upload").click();
    };
} ]), funnyControllers.controller("CreateFunnyPictureController", [ "$scope", "$window", "$http", "$mdDialog", "$mdToast", "SharedProperties", "Pictures", "Funnies", function($scope, $window, $http, $mdDialog, $mdToast, SharedProperties, Pictures, Funnies) {
    $scope.template = {}, $scope.funniesByTemplate = {}, $scope.currentPage = 1, $scope.totalItems = 0, 
    $scope.itemsPerPage = 6, $scope.thumbnailType = "MEDIUM", Pictures.get({
        id: SharedProperties.getTemplateId()
    }, function(picture) {
        $scope.template = picture, $http({
            url: SharedProperties.getApiUrl() + "/pictures/" + $scope.template.id + "/funniesThumb",
            method: "GET",
            params: {
                offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                limit: $scope.itemsPerPage,
                thumbnailType: $scope.thumbnailType
            }
        }).success(function(data) {
            $scope.funniesByTemplate = data.entities, $scope.totalItems = data.count;
        }).error(function(error) {
            $mdToast.show($mdToast.simple().content(error.statusText + " " + error.status).position("bottom left").hideDelay(5e3));
        });
    }), $scope.createFunnyPicture = function() {
        var postObject = new Object();
        postObject.name = $scope.template.name, postObject.header = $scope.headerText, postObject.footer = $scope.footerText, 
        postObject.template = {}, postObject.template.id = $scope.template.id, Funnies.save(postObject, function(data) {
            $scope.template = data;
        }, function(error) {
            $mdToast.show($mdToast.simple().content("Can't create picture " + error.statusText + " " + error.status).position("bottom left").hideDelay(5e3));
        });
    }, $scope.pageChanged = function() {
        $http({
            url: SharedProperties.getApiUrl() + "/pictures/" + $scope.template.id + "/funnies",
            method: "GET",
            params: {
                offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
                limit: $scope.itemsPerPage
            }
        }).success(function(data) {
            $scope.funniesByTemplate = data.entities, $scope.totalItems = data.count;
        }).error(function(error) {
            $mdToast.show($mdToast.simple().content(error.statusText + " " + error.status).position("bottom left").hideDelay(5e3));
        });
    }, $scope.modalOpen = function(funnyThumbnail) {
        $mdDialog.show({
            controller: "FunnyPreviewModalController",
            templateUrl: "html/modal/funnyPreviewModal.tmpl.html",
            resolve: {
                funnyThumbnail: function() {
                    return funnyThumbnail;
                }
            }
        });
    }, $scope.isButtonDisabled = function() {
        return !$scope.funnyPictureText.$valid;
    }, $scope.fullSize = function() {
        $window.open($scope.template.url);
    }, $scope.showPagination = function() {
        return $scope.totalItems > $scope.itemsPerPage;
    };
} ]), funnyControllers.controller("ContactController", [ "$scope", "$mdToast", "Feedback", function($scope, $mdToast, Feedback) {
    $scope.feedback = {}, $scope.sendFeedback = function() {
        Feedback.save($scope.feedback, function() {
            $mdToast.show($mdToast.simple().content("Thank you " + $scope.feedback.name + ", your feedback was sent.").position("bottom left").hideDelay(5e3));
        }, function(error) {
            $mdToast.show($mdToast.simple().content("Feedback can't sent " + error.statusText + " " + error.status).position("bottom left").hideDelay(5e3));
        });
    }, $scope.isButtonDisabled = function() {
        return !$scope.contactForm.$valid;
    };
} ]);