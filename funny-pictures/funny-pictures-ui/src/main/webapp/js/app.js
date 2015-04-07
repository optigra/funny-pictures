!function() {
    "use strict";
    angular.module("app", [ "app.core", "app.contact", "app.funnies", "app.pictures", "app.header", "app.footer", "app.authorize" ]);
}(), function() {
    "use strict";
    angular.module("app.core", [ "ngResource", "ngRoute", "ngMaterial", "ngClipboard", "pascalprecht.translate", "wu.masonry", "ui.bootstrap", "blocks.exception", "blocks.logger" ]);
}(), function() {
    "use strict";
    function config($routeProvider) {
        $routeProvider.when("/home", {
            templateUrl: "html/funnies.html",
            controller: "FunniesController",
            controllerAs: "vm"
        }).when("/templates", {
            templateUrl: "html/pictures.html",
            controller: "PicturesController",
            controllerAs: "vm"
        }).when("/createTemplate", {
            templateUrl: "html/createPicture.html",
            controller: "CreatePictureController",
            controllerAs: "vm"
        }).when("/createFunnyPicture/:pictureId", {
            templateUrl: "html/createFunnyPicture.html",
            controller: "CreateFunnyController",
            controllerAs: "vm"
        }).when("/preview/:funnyPictureId", {
            templateUrl: "html/funnyPicturePreview.html",
            controller: "PreviewFunnyController",
            controllerAs: "vm"
        }).when("/contact", {
            templateUrl: "html/contact.html",
            controller: "ContactController",
            controllerAs: "vm"
        }).when("/authorize", {
            templateUrl: "html/authorize.html",
            controller: "AuthorizeController",
            controllerAs: "vm"
        }).otherwise({
            redirectTo: "/home"
        });
    }
    angular.module("app.core").config(config), config.$inject = [ "$routeProvider" ];
}(), function() {
    "use strict";
    function getItemsPerPage() {
        var itemsPerPage = 15;
        return window.innerWidth >= 960 && window.innerWidth < 1200 ? itemsPerPage = 25 : window.innerWidth >= 1200 && (itemsPerPage = 30), 
        itemsPerPage;
    }
    angular.module("app.core").value("values", {
        thumbnailType: "MEDIUM",
        itemsPerPage: getItemsPerPage()
    });
}(), function() {
    "use strict";
    angular.module("app.core").constant("constants", {
        apiUrl: "http://localhost:8080/funny-pictures-rest-api/api"
    });
}(), function() {
    "use strict";
    angular.module("blocks.exception", [ "blocks.logger", "ngMaterial" ]);
}(), function() {
    "use strict";
    function extendExceptionHandler($injector) {
        return function(exception) {
            var logger = $injector.get("logger");
            logger.error(exception.statusText, exception.stack);
        };
    }
    angular.module("blocks.exception").factory("$exceptionHandler", extendExceptionHandler), 
    extendExceptionHandler.$inject = [ "$injector" ];
}(), function() {
    "use strict";
    angular.module("blocks.logger", [ "ngMaterial" ]);
}(), function() {
    "use strict";
    function logger($log, $mdToast) {
        function error(message, data) {
            $mdToast.show($mdToast.simple().content("Error: " + message).position(position).hideDelay(hideDelay)), 
            $log.error("Error: " + message, data);
        }
        function info(message, data) {
            $mdToast.show($mdToast.simple().content("Info: " + message).position(position).hideDelay(hideDelay)), 
            $log.info("Info: " + message, data);
        }
        function success(message, data) {
            $mdToast.show($mdToast.simple().content("Success: " + message).position(position).hideDelay(hideDelay)), 
            $log.info("Success: " + message, data);
        }
        function warning(message, data) {
            $mdToast.show($mdToast.simple().content("Warning: " + message).position(position).hideDelay(hideDelay)), 
            $log.warn("Warning: " + message, data);
        }
        var position = "bottom left", hideDelay = 5e3, service = {
            showToasts: !0,
            error: error,
            info: info,
            success: success,
            warning: warning,
            log: $log.log
        };
        return service;
    }
    angular.module("blocks.logger").factory("logger", logger), logger.$inject = [ "$log", "$mdToast" ];
}(), function() {
    "use strict";
    function clickLink($location) {
        return {
            link: function(scope, element, attrs) {
                element.on("click", function() {
                    scope.$apply(function() {
                        $location.path(attrs.clickLink);
                    });
                });
            }
        };
    }
    angular.module("app").directive("clickLink", clickLink), clickLink.$inject = [ "$location" ];
}(), function() {
    "use strict";
    function clickSelector() {
        return {
            link: function(scope, element, attrs) {
                element.on("click", function() {
                    scope.$apply(function() {
                        $(attrs.clickSelector).click();
                    });
                });
            }
        };
    }
    angular.module("app").directive("clickSelector", clickSelector);
}(), function() {
    "use strict";
    function fileModel($parse) {
        return {
            restrict: "A",
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel), modelSetter = model.assign;
                element.bind("change", function() {
                    var reader = new FileReader();
                    reader.onload = function(e) {
                        var image = $("#image-preview");
                        image.fadeOut("fast", function() {
                            image.attr("src", e.target.result), image.removeClass("hidden"), image.fadeIn("fast");
                        });
                    }, reader.readAsDataURL(element[0].files[0]), scope.$apply(function() {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }
    angular.module("app").directive("fileModel", fileModel), fileModel.$inject = [ "$parse" ];
}(), function() {
    "use strict";
    function reloadDisqus($timeout, $location) {
        return {
            link: function($scope, element, attrs) {
                $scope.$on("dataloaded", function() {
                    $timeout(function() {
                        DISQUS.reset({
                            reload: !0,
                            config: function() {
                                this.page.identifier = attrs.reloadDisqus, this.page.url = $location.absUrl();
                            }
                        });
                    }, 1e3, !1);
                });
            }
        };
    }
    angular.module("app").directive("reloadDisqus", reloadDisqus), reloadDisqus.$inject = [ "$timeout", "$location" ];
}(), function() {
    "use strict";
    angular.module("app.header", [ "app.core" ]);
}(), function() {
    "use strict";
    function header() {
        return {
            restrict: "E",
            templateUrl: "html/directives/header.html"
        };
    }
    angular.module("app.header").directive("header", header);
}(), function() {
    "use strict";
    function HeaderController($location, $mdSidenav) {
        function isActive(viewLocation) {
            return viewLocation === $location.path();
        }
        function toggleMenu() {
            $mdSidenav("left").toggle();
        }
        var vm = this;
        vm.isActive = isActive, vm.toggleMenu = toggleMenu;
    }
    angular.module("app.header").controller("HeaderController", HeaderController), HeaderController.$inject = [ "$location", "$mdSidenav" ];
}(), function() {
    "use strict";
    angular.module("app.footer", [ "app.core" ]);
}(), function() {
    "use strict";
    function footer() {
        return {
            restrict: "E",
            templateUrl: "html/directives/footer.html"
        };
    }
    angular.module("app.footer").directive("footer", footer);
}(), function() {
    "use strict";
    function FooterController($translate) {
        function changeLanguage(langKey) {
            $translate.use(langKey);
        }
        function getCurrentLanguage() {
            return $translate.use();
        }
        var vm = this;
        vm.changeLanguage = changeLanguage, vm.getCurrentLanguage = getCurrentLanguage;
    }
    angular.module("app.footer").controller("FooterController", FooterController), FooterController.$inject = [ "$translate" ];
}(), function() {
    "use strict";
    angular.module("app.funnies", [ "app.core", "app.tags" ]);
}(), function() {
    "use strict";
    function FunniesFactory($resource, constants) {
        return $resource(constants.apiUrl + "/funnies/:id", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.funnies").factory("FunniesFactory", FunniesFactory), FunniesFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FunnyThumbnailByFunnyPictureFactory($resource, constants) {
        return $resource(constants.apiUrl + "/funnies/:id/funnyThumb", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.funnies").factory("FunnyThumbnailByFunnyPictureFactory", FunnyThumbnailByFunnyPictureFactory), 
    FunnyThumbnailByFunnyPictureFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FunnyThumbnailsFactory($resource, constants) {
        return $resource(constants.apiUrl + "/funniesthumb/:id", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.funnies").factory("FunnyThumbnailsFactory", FunnyThumbnailsFactory), 
    FunnyThumbnailsFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FunnyThumbnailsByPictureFactory($resource, constants) {
        return $resource(constants.apiUrl + "/funnies/:id/funniesThumbs", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.funnies").factory("FunnyThumbnailsByPictureFactory", FunnyThumbnailsByPictureFactory), 
    FunnyThumbnailsByPictureFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FunniesController($exceptionHandler, values, FunnyThumbnailsFactory) {
        function pageChanged() {
            FunnyThumbnailsFactory.query({
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funnyThumbnails = data.entities, vm.totalItems = data.count;
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        var vm = this;
        vm.funnyThumbnails = {}, vm.totalItems = 0, vm.currentPage = 1, vm.itemsPerPage = values.itemsPerPage, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, pageChanged();
    }
    angular.module("app.funnies").controller("FunniesController", FunniesController), 
    FunniesController.$inject = [ "$exceptionHandler", "values", "FunnyThumbnailsFactory" ];
}(), function() {
    "use strict";
    function CreateFunnyController($scope, $window, $routeParams, $location, $exceptionHandler, logger, values, PicturesFactory, FunniesFactory, FunnyThumbnailsByPictureFactory, TagsFactory) {
        function activate() {
            PicturesFactory.get({
                id: $routeParams.pictureId
            }, function(picture) {
                vm.picture = picture, pageChanged();
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function pageChanged() {
            FunnyThumbnailsByPictureFactory.query({
                id: vm.picture.id,
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funniesByTemplate = data.entities, vm.totalItems = data.count, $scope.$broadcast("dataloaded");
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function createFunnyPicture() {
            vm.progress = !0;
            var postObject = new Object();
            postObject.name = vm.picture.name, postObject.header = vm.headerText, postObject.footer = vm.footerText, 
            postObject.template = {}, postObject.template.id = vm.picture.id, FunniesFactory.save(postObject, function(data) {
                vm.funnyPicture = data, vm.progress = !1, vm.loaded = !0, vm.currentFunnyLocation = currentUrl + vm.funnyPicture.id, 
                pageChanged();
            }, function(e) {
                vm.progress = !1, $exceptionHandler(e);
            });
        }
        function createTag() {
            var postObject = new Object();
            postObject.name = vm.tag, TagsFactory.save(postObject, function(data) {
                vm.tags = data;
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function createNew() {
            vm.headerText = "", vm.footerText = "", vm.loaded = !1, vm.funnyPicture = {};
        }
        function cancel() {
            FunniesFactory["delete"]({
                id: vm.funnyPicture.id
            }, function() {
                vm.loaded = !1, vm.funnyPicture = {}, vm.pageChanged(), logger.info("Deleted Funny picture");
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        function isButtonDisabled() {
            return !vm.funnyPictureText.$valid;
        }
        function shareSocial(baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent(vm.currentFunnyLocation);
            event.preventDefault(), $window.open(url, "_blank", "width=" + width + ",height=" + height);
        }
        function clipCopyMessage() {
            logger.info("Link is copied to clipboard");
        }
        var vm = this, currentUrl = $location.absUrl().split("#")[0] + "#/preview/";
        vm.picture = {}, vm.headerText = "", vm.footerText = "", vm.funnyPicture = {}, vm.funniesByTemplate = {}, 
        vm.totalItems = 0, vm.currentPage = 1, vm.itemsPerPage = 6, vm.progress = !1, vm.loaded = !1, 
        vm.currentFunnyLocation = currentUrl, vm.pageChanged = pageChanged, vm.showPagination = showPagination, 
        vm.createFunnyPicture = createFunnyPicture, vm.createNew = createNew, vm.cancel = cancel, 
        vm.isButtonDisabled = isButtonDisabled, vm.shareSocial = shareSocial, vm.clipCopyMessage = clipCopyMessage, 
        vm.createTag = createTag, activate();
    }
    angular.module("app.funnies").controller("CreateFunnyController", CreateFunnyController), 
    CreateFunnyController.$inject = [ "$scope", "$window", "$routeParams", "$location", "$exceptionHandler", "logger", "values", "PicturesFactory", "FunniesFactory", "FunnyThumbnailsByPictureFactory", "TagsFactory" ];
}(), function() {
    "use strict";
    function PreviewFunnyController($scope, $window, $location, $routeParams, logger, $exceptionHandler, values, FunniesFactory, FunnyThumbnailByFunnyPictureFactory, FunnyThumbnailsByPictureFactory) {
        function activate() {
            FunnyThumbnailByFunnyPictureFactory.query({
                id: $routeParams.funnyPictureId,
                thumbnailType: thumbnailType
            }, function(funnyThumbnail) {
                vm.funnyThumbnail = funnyThumbnail, pageChanged(), $scope.$broadcast("dataloaded");
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function pageChanged() {
            FunniesFactory.get({
                id: $routeParams.funnyPictureId
            }, function(data) {
                vm.funnyPicture = data, FunnyThumbnailsByPictureFactory.query({
                    id: vm.funnyPicture.template.id,
                    offset: (vm.currentPage - 1) * vm.itemsPerPage,
                    limit: vm.itemsPerPage,
                    thumbnailType: values.thumbnailType
                }, function(data) {
                    vm.funniesByTemplate = data.entities, vm.totalItems = data.count;
                }, function(e) {
                    $exceptionHandler(e);
                });
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        function swapFunnyPicture(funnyThumbnail) {
            $location.path("/preview/" + funnyThumbnail.funnyPictureId, !1), vm.currentLocation = currentUrl + funnyThumbnail.funnyPictureId, 
            FunnyThumbnailByFunnyPictureFactory.query({
                id: $routeParams.funnyPictureId,
                thumbnailType: thumbnailType
            }, function(funnyThumbnail) {
                vm.funnyThumbnail = funnyThumbnail;
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function shareSocial(baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent(vm.currentLocation);
            event.preventDefault(), $window.open(url, "_blank", "width=" + width + ",height=" + height);
        }
        function clipCopyMessage() {
            logger.info("Link is copied to clipboard");
        }
        var vm = this, currentUrl = $location.absUrl().split("#")[0] + "#/preview/", thumbnailType = "BIG";
        vm.funnyPicture = {}, vm.funnyThumbnail = {}, vm.funniesByTemplate = {}, vm.totalItems = 0, 
        vm.currentPage = 1, vm.itemsPerPage = 6, vm.currentLocation = currentUrl + $routeParams.funnyPictureId, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, vm.swapFunnyPicture = swapFunnyPicture, 
        vm.shareSocial = shareSocial, vm.clipCopyMessage = clipCopyMessage, activate();
    }
    angular.module("app.funnies").controller("PreviewFunnyController", PreviewFunnyController), 
    PreviewFunnyController.$inject = [ "$scope", "$window", "$location", "$routeParams", "logger", "$exceptionHandler", "values", "FunniesFactory", "FunnyThumbnailByFunnyPictureFactory", "FunnyThumbnailsByPictureFactory" ];
}(), function() {
    "use strict";
    angular.module("app.pictures", [ "app.core" ]);
}(), function() {
    "use strict";
    function PicturesFactory($resource, constants) {
        return $resource(constants.apiUrl + "/pictures/:id", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.pictures").factory("PicturesFactory", PicturesFactory), PicturesFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function PictureThumbnailsFactory($resource, constants) {
        return $resource(constants.apiUrl + "/picturesthumb/:id", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.pictures").factory("PictureThumbnailsFactory", PictureThumbnailsFactory), 
    PictureThumbnailsFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FileUploadService($http, constants) {
        var uploadUrl = constants.apiUrl + "/content";
        this.uploadFileToUrl = function(file) {
            var fd = new FormData();
            return fd.append("content", file), $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {
                    content: "file",
                    "Content-Type": void 0
                }
            });
        }, this.uploadFileUrlToUrl = function(urlToFile) {
            return $http({
                url: uploadUrl,
                method: "POST",
                headers: {
                    content: "url"
                },
                params: {
                    url: urlToFile
                }
            });
        };
    }
    angular.module("app.pictures").service("FileUploadService", FileUploadService), 
    FileUploadService.$inject = [ "$http", "constants" ];
}(), function() {
    "use strict";
    function PicturesController($exceptionHandler, values, PictureThumbnailsFactory) {
        function pageChanged() {
            PictureThumbnailsFactory.query({
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.pictureThumbnails = data.entities, vm.totalItems = data.count;
            }, function(e) {
                $exceptionHandler(e);
            });
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        var vm = this;
        vm.pictureThumbnails = {}, vm.totalItems = 0, vm.currentPage = 1, vm.itemsPerPage = values.itemsPerPage, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, pageChanged();
    }
    angular.module("app.pictures").controller("PicturesController", PicturesController), 
    PicturesController.$inject = [ "$exceptionHandler", "values", "PictureThumbnailsFactory" ];
}(), function() {
    "use strict";
    function CreatePictureController($location, $exceptionHandler, logger, FileUploadService, PicturesFactory) {
        function uploadFile() {
            var file = vm.myFile, urlToFile = vm.pictureUrl;
            if (vm.progress = !0, file || urlToFile) {
                var promiseFile = {};
                file ? promiseFile = FileUploadService.uploadFileToUrl(file) : urlToFile && (promiseFile = FileUploadService.uploadFileUrlToUrl(urlToFile)), 
                promiseFile.then(function(response) {
                    var pictureObject = {
                        name: vm.pictureTitle,
                        url: response.data.path
                    };
                    PicturesFactory.save(pictureObject, function(data) {
                        vm.progress = !1, vm.loaded = !0, logger.success("File " + data.name + " uploaded to server."), 
                        $location.path("/createFunnyPicture/" + data.id);
                    }, function(e) {
                        vm.progress = !1, $exceptionHandler(e);
                    });
                });
            } else vm.progress = !1, $exceptionHandler("Bad url or file");
        }
        function isButtonDisabled() {
            return !vm.templateInputForm.$valid;
        }
        var vm = this;
        vm.pictureTitle = "", vm.pictureUrl = "", vm.progress = !1, vm.loaded = !1, vm.isButtonDisabled = isButtonDisabled, 
        vm.uploadFile = uploadFile;
    }
    angular.module("app.pictures").controller("CreatePictureController", CreatePictureController), 
    CreatePictureController.$inject = [ "$location", "$exceptionHandler", "logger", "FileUploadService", "PicturesFactory" ];
}(), function() {
    "use strict";
    angular.module("app.tags", [ "app.core" ]);
}(), function() {
    "use strict";
    function TagsFactory($resource, constants) {
        return $resource(constants.apiUrl + "/tags/:id", {}, {
            query: {
                method: "GET",
                isArray: !0
            }
        });
    }
    angular.module("app.tags").factory("TagsFactory", TagsFactory), TagsFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    angular.module("app.authorize", [ "app.core" ]);
}(), function() {
    "use strict";
    function AuthorizeController() {
        function authorize() {
            vm.progress = !0;
        }
        function isButtonDisabled() {
            return !vm.authorizeForm.$valid;
        }
        var vm = this;
        vm.username = "", vm.password = "", vm.authorize = authorize, vm.isButtonDisabled = isButtonDisabled;
    }
    angular.module("app.authorize").controller("AuthorizeController", AuthorizeController), 
    AuthorizeController.$inject = [ "$resource", "$exceptionHandler", "logger" ];
}(), function() {
    "use strict";
    angular.module("app.contact", [ "app.core" ]);
}(), function() {
    "use strict";
    function FeedbacksFactory($resource, constants) {
        return $resource(constants.apiUrl + "/feedbacks/:id", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app.contact").factory("FeedbacksFactory", FeedbacksFactory), FeedbacksFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function ContactController($exceptionHandler, logger, FeedbacksFactory) {
        function sendFeedback() {
            vm.progress = !0, FeedbacksFactory.save(vm.feedback, function() {
                vm.progress = !1, logger.success("Thank you " + vm.feedback.name + ", your feedback was sent."), 
                vm.feedback = {};
            }, function(e) {
                vm.progress = !1, $exceptionHandler(e);
            });
        }
        function isButtonDisabled() {
            return !vm.contactForm.$valid;
        }
        var vm = this;
        vm.feedback = {}, vm.progress = !1, vm.sendFeedback = sendFeedback, vm.isButtonDisabled = isButtonDisabled;
    }
    angular.module("app.contact").controller("ContactController", ContactController), 
    ContactController.$inject = [ "$exceptionHandler", "logger", "FeedbacksFactory" ];
}(), function() {
    "use strict";
    function translation($translateProvider) {
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
            HOME_HEADER: "Home",
            TEMPLATES_HEADER: "Templates",
            CREATE_TEMPLATE_HEADER: "Create template",
            CONTACT_US_HEADER: "Contact us",
            COMMENTS_FALLBACK_1: "Please enable JavaScript to view the",
            COMMENTS_FALLBACK_2: "comments powered by Disqus.",
            PREVIOUS_LABEL: "Previous",
            NEXT_LABEL: "Next",
            LINK_TO_IMAGE: "Direct link to image",
            LINK_TO_PREVIEW: "Link to preview",
            COPY_LABEL: "Copy",
            TAGS_LABEL: "Tags",
            AUTHORIZE_HEADER: "Login",
            USERNAME_LABEL: "Username",
            PASSWORD_LABEL: "Password",
            AUTHORIZE_BUTTON_HEADER: "Sign in",
            ENGLISH_LABEL: "English",
            UKRAINIAN_LABEL: "Українська"
        }), $translateProvider.translations("uk", {
            CONTACT_HEADER: "Зв'яжіться з нами",
            NAME_LABEL: "Ім'я",
            EMAIL_LABEL: "Електронна пошта",
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
            HOME_HEADER: "Головна",
            TEMPLATES_HEADER: "Шаблони",
            CREATE_TEMPLATE_HEADER: "Створити шаблон",
            CONTACT_US_HEADER: "Контакти",
            COMMENTS_FALLBACK_1: "Будь ласка, включіть JavaScript для перегляду",
            COMMENTS_FALLBACK_2: "Disqus коментарів.",
            PREVIOUS_LABEL: "Попередня",
            NEXT_LABEL: "Наступна",
            LINK_TO_IMAGE: "Пряме посилання на картинку",
            LINK_TO_PREVIEW: "Посилання на попередній перегляд",
            COPY_LABEL: "Копіювати",
            TAGS_LABEL: "Теги",
            AUTHORIZE_HEADER: "Авторизація",
            USERNAME_LABEL: "Ім’я користувача",
            PASSWORD_LABEL: "Пароль",
            AUTHORIZE_BUTTON_HEADER: "Авторизуватися",
            ENGLISH_LABEL: "English",
            UKRAINIAN_LABEL: "Українська"
        }), $translateProvider.determinePreferredLanguage(), $translateProvider.fallbackLanguage("en_US");
    }
    angular.module("app").config(translation), translation.$inject = [ "$translateProvider" ];
}();