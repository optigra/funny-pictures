var funnyControllers = angular.module("funnyControllers", [ "pascalprecht.translate" ]);

funnyControllers.config([ "$translateProvider", function($translateProvider) {
    $translateProvider.translations("en_US", {
        CONTACT_HEADER: "Feel free to contact us",
        NAME_LABEL: "Name",
        EMAIL_LABEL: "Email",
        SUBJECT_LABEL: "Subject",
        MESSAGE_LABEL: "Message",
        SEND_MESSAGE_BUTTON: "Send Message",
        OUR_CONTACTS_LABEL: "Our contacts",
        ADDRESS: "12 Storozhenka St., Lviv",
        CITY: "[Galicia] Ukraine",
        UPLOAD_PICTURE_HEADER: "Upload your template",
        TEMPLATE_TITLE_LABEL: "Template title",
        IMAGE_URL_LABEL: "Input image url",
        OR_LABEL: "or",
        OPEN_IMAGE_BUTTON: "Open image",
        FILE_NAME_LABEL: "File name",
        UPLOAD_IMAGE_BUTTON: "Upload file",
        DOWNLOAD_LABEL: "Download",
        SHARE_LABEL: "Share",
        HEADER_TEXT_LABEL: "Header text",
        FOOTER_TEXT_LABEL: "Footer text",
        CREATE_FUNNY_BUTTON: "Create picture",
        CANCEL_BUTTON: "Cancel",
        CREATE_NEW_BUTTON: "Create new",
        HOME_HEADER: "Templates",
        CREATE_TEMPLATE_HEADER: "Create template",
        FUNNY_PICTURES_HEADER: "Funny pictures",
        CONTACT_US_HEADER: "Contact us"
    }), $translateProvider.translations("uk", {
        CONTACT_HEADER: "Зв'яжіться з нами",
        NAME_LABEL: "Ім'я",
        EMAIL_LABEL: "Email",
        SUBJECT_LABEL: "Тема",
        MESSAGE_LABEL: "Повідомлення",
        SEND_MESSAGE_BUTTON: "Відслати",
        OUR_CONTACTS_LABEL: "Наші контакти",
        ADDRESS: "вул.Стороженка 12, Львів",
        CITY: "[Галичина] Україна",
        UPLOAD_PICTURE_HEADER: "Завантажте ваше зображення",
        TEMPLATE_TITLE_LABEL: "Назва картинки",
        IMAGE_URL_LABEL: "Введіть URL-адресу зображення",
        OR_LABEL: "або",
        OPEN_IMAGE_BUTTON: "Відкрити зображення",
        FILE_NAME_LABEL: "Назва файлу",
        UPLOAD_IMAGE_BUTTON: "Завантажити зображення",
        DOWNLOAD_LABEL: "Завантажити",
        SHARE_LABEL: "Поділитися",
        HEADER_TEXT_LABEL: "Верхній текст",
        FOOTER_TEXT_LABEL: "Нижній текст",
        CREATE_FUNNY_BUTTON: "Створити мем",
        CANCEL_BUTTON: "Відмінити",
        CREATE_NEW_BUTTON: "Створити новий мем",
        HOME_HEADER: "Шаблони",
        CREATE_TEMPLATE_HEADER: "Створити шаблон",
        FUNNY_PICTURES_HEADER: "Картинки",
        CONTACT_US_HEADER: "Контакти"
    }), $translateProvider.determinePreferredLanguage(), $translateProvider.fallbackLanguage("en_US");
} ]), funnyControllers.controller("HomeController", [ "$scope", "$location", "$mdToast", "PicturesThumbnails", "Funnies", "SharedProperties", function($scope, $location, $mdToast, PicturesThumbnails) {
    $scope.headerText = "", $scope.footerText = "", $scope.currentPage = 1, $scope.totalItems = 0, 
    $scope.itemsPerPage = 9, $scope.thumbnailType = "MEDIUM", PicturesThumbnails.query({
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
        $location.path("/createFunnyPicture/" + templateId);
    }, $scope.go = function(path) {
        $location.path(path);
    };
} ]), funnyControllers.controller("HeaderController", [ "$scope", "$location", "$translate", function($scope, $location, $translate) {
    $scope.isActive = function(viewLocation) {
        return viewLocation === $location.path();
    }, $scope.changeLanguage = function(langKey) {
        $translate.use(langKey);
    }, $scope.go = function(path) {
        $location.path(path);
    };
} ]), funnyControllers.controller("FunniesController", [ "$scope", "$mdToast", "FunnyPicturesThumbnails", function($scope, $mdToast, FunnyPicturesThumbnails) {
    $scope.funniesThumbnails = {}, $scope.currentPage = 1, $scope.totalItems = 0, $scope.itemsPerPage = 9, 
    $scope.thumbnailType = "MEDIUM", FunnyPicturesThumbnails.query({
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
} ]), funnyControllers.controller("PreviewFunnyController", [ "$scope", "$routeParams", "$http", "$location", "$window", "$mdToast", "SharedProperties", "Funnies", function($scope, $routeParams, $http, $location, $window, $mdToast, SharedProperties, Funnies) {
    $scope.funnyPicture = {}, $scope.funniesByTemplate = {}, $scope.currentPage = 1, 
    $scope.totalItems = 0, $scope.itemsPerPage = 6, $scope.thumbnailType = "MEDIUM", 
    $scope.currentUrl = $location.absUrl().split("#")[0] + "#/preview/", $scope.currentLocation = $scope.currentUrl + $routeParams.funnyPictureId, 
    Funnies.get({
        id: $routeParams.funnyPictureId
    }, function(funnyPicture) {
        $scope.funnyPicture = funnyPicture, $http({
            url: SharedProperties.getApiUrl() + "/pictures/" + $scope.funnyPicture.template.id + "/funniesThumb",
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
    }, function(error) {
        $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
    }), $scope.pageChanged = function() {
        $http({
            url: SharedProperties.getApiUrl() + "/pictures/" + $scope.funnyPicture.template.id + "/funniesThumb",
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
    }, $scope.swapFunnyPicture = function(funnyThumbnail) {
        $location.path("/preview/" + funnyThumbnail.funnyPictureId, !1), $scope.currentLocation = $scope.currentUrl + funnyThumbnail.funnyPictureId, 
        Funnies.get({
            id: funnyThumbnail.funnyPictureId
        }, function(funnyPicture) {
            $scope.funnyPicture = funnyPicture;
        }, function(error) {
            $mdToast.show($mdToast.simple().content("Error " + error.message).position("bottom left").hideDelay(5e3));
        });
    }, $scope.shareSocial = function(baseUrl, width, height) {
        var url = baseUrl + encodeURIComponent($scope.currentLocation);
        event.preventDefault(), $window.open(url, "_blank", "width=" + width + ",height=" + height);
    }, $scope.showPagination = function() {
        return $scope.totalItems > $scope.itemsPerPage;
    };
} ]), funnyControllers.controller("CreatePictureController", [ "$scope", "$location", "$window", "$mdToast", "FileUpload", "SharedProperties", "Pictures", function($scope, $location, $window, $mdToast, FileUpload, SharedProperties, Pictures) {
    $scope.pictureTitle = "", $scope.pictureUrl = "", $scope.uploadFile = function() {
        var uploadUrl = SharedProperties.getApiUrl() + "/content", file = $scope.myFile, urlToFile = $scope.pictureUrl;
        if (file || urlToFile) {
            var promiseFile = {};
            file ? promiseFile = FileUpload.uploadFileToUrl(file, uploadUrl) : urlToFile && (promiseFile = FileUpload.uploadFileUrlToUrl(urlToFile, uploadUrl)), 
            promiseFile.then(function(data) {
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
            });
        } else $mdToast.show($mdToast.simple().content("Please select the image!").position("bottom left").hideDelay(5e3));
    }, $scope.isButtonDisabled = function() {
        return !$scope.templateInputForm.$valid;
    }, $scope.callUpload = function() {
        $("#upload").click();
    };
} ]), funnyControllers.controller("CreateFunnyPictureController", [ "$scope", "$routeParams", "$window", "$http", "$mdDialog", "$mdToast", "SharedProperties", "Pictures", "Funnies", function($scope, $routeParams, $window, $http, $mdDialog, $mdToast, SharedProperties, Pictures, Funnies) {
    $scope.template = {}, $scope.funnyPicture = {}, $scope.funniesByTemplate = {}, $scope.currentPage = 1, 
    $scope.totalItems = 0, $scope.itemsPerPage = 6, $scope.thumbnailType = "MEDIUM", 
    $scope.progress = !1, $scope.loaded = !1, Pictures.get({
        id: $routeParams.templateId
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
    }), $scope.pageChanged = function() {
        $http({
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
    }, $scope.createFunnyPicture = function() {
        $scope.progress = !0;
        var postObject = new Object();
        postObject.name = $scope.template.name, postObject.header = $scope.headerText, postObject.footer = $scope.footerText, 
        postObject.template = {}, postObject.template.id = $scope.template.id, Funnies.save(postObject, function(data) {
            $scope.funnyPicture = data, $scope.pageChanged(), $scope.progress = !1, $scope.loaded = !0;
        }, function(error) {
            $scope.progress = !1, $mdToast.show($mdToast.simple().content("Can't create picture " + error.statusText + " " + error.status).position("bottom left").hideDelay(5e3));
        });
    }, $scope.cancel = function() {
        $http({
            url: SharedProperties.getApiUrl() + "/funnies/" + $scope.funnyPicture.id,
            method: "DELETE"
        }).success(function() {
            $scope.pageChanged(), $scope.loaded = !1, $scope.funnyPicture = {};
        }).error(function(error) {
            $mdToast.show($mdToast.simple().content("Funny picture has not been deleted " + error.statusText + " " + error.status).position("bottom left").hideDelay(5e3));
        });
    }, $scope.createNew = function() {
        $scope.headerText = "", $scope.footerText = "", $scope.loaded = !1, $scope.funnyPicture = {};
    }, $scope.isButtonDisabled = function() {
        return !$scope.funnyPictureText.$valid;
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