!function() {
    "use strict";
    angular.module("app", [ "app.core" ]);
}(), function() {
    "use strict";
    angular.module("app.core", [ "ngResource", "ngRoute", "ngMaterial", "pascalprecht.translate", "wu.masonry", "ui.bootstrap" ]);
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
    function footer() {
        return {
            restrict: "E",
            templateUrl: "html/directives/footer.html"
        };
    }
    angular.module("app").directive("footer", footer);
}(), function() {
    "use strict";
    function header() {
        return {
            restrict: "E",
            templateUrl: "html/directives/header.html"
        };
    }
    angular.module("app").directive("header", header);
}(), function() {
    "use strict";
    function HeaderController($location, $mdSidenav, $translate) {
        function isActive(viewLocation) {
            return viewLocation === $location.path();
        }
        function changeLanguage(langKey) {
            $translate.use(langKey);
        }
        function getCurrentLanguage() {
            return $translate.use();
        }
        function toggleMenu() {
            $mdSidenav("left").toggle();
        }
        var vm = this;
        vm.isActive = isActive, vm.changeLanguage = changeLanguage, vm.getCurrentLanguage = getCurrentLanguage, 
        vm.toggleMenu = toggleMenu;
    }
    angular.module("app").controller("HeaderController", HeaderController), HeaderController.$inject = [ "$location", "$mdSidenav", "$translate" ];
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
    angular.module("app").factory("FunniesFactory", FunniesFactory), FunniesFactory.$inject = [ "$resource", "constants" ];
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
    angular.module("app").factory("FunnyThumbnailsFactory", FunnyThumbnailsFactory), 
    FunnyThumbnailsFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FunnyThumbnailsByPicture($resource, constants) {
        return $resource(constants.apiUrl + "/funnies/:id/funniesThumb", {}, {
            query: {
                method: "GET",
                isArray: !1
            }
        });
    }
    angular.module("app").factory("FunnyThumbnailsByPicture", FunnyThumbnailsByPicture), 
    FunnyThumbnailsByPicture.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function FunniesController(values, FunnyThumbnailsFactory) {
        function pageChanged() {
            FunnyThumbnailsFactory.query({
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funnyThumbnails = data.entities, vm.totalItems = data.count;
            }, function() {});
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        var vm = this;
        vm.funnyThumbnails = {}, vm.totalItems = 0, vm.currentPage = 1, vm.itemsPerPage = values.itemsPerPage, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, pageChanged();
    }
    angular.module("app").controller("FunniesController", FunniesController), FunniesController.$inject = [ "values", "FunnyThumbnailsFactory" ];
}(), function() {
    "use strict";
    function CreateFunnyController($routeParams, values, PicturesFactory, FunniesFactory, FunnyThumbnailsByPicture) {
        function activate() {
            PicturesFactory.get({
                id: $routeParams.pictureId
            }, function(picture) {
                vm.picture = picture, pageChanged();
            }, function() {});
        }
        function pageChanged() {
            FunnyThumbnailsByPicture.query({
                id: vm.picture.id,
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funniesByTemplate = data.entities, vm.totalItems = data.count;
            }, function() {});
        }
        function createFunnyPicture() {
            vm.progress = !0;
            var postObject = new Object();
            postObject.name = vm.picture.name, postObject.header = vm.headerText, postObject.footer = vm.footerText, 
            postObject.template = {}, postObject.template.id = vm.picture.id, FunniesFactory.save(postObject, function(data) {
                vm.funnyPicture = data, vm.progress = !1, vm.loaded = !0, pageChanged();
            }, function() {
                vm.progress = !1;
            });
        }
        function createNew() {
            vm.headerText = "", vm.footerText = "", vm.loaded = !1, vm.funnyPicture = {};
        }
        function cancel() {
            FunniesFactory["delete"]({
                id: vm.funnyPicture.id
            }, function() {
                vm.loaded = !1, vm.funnyPicture = {}, vm.pageChanged();
            }, function() {});
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        function isButtonDisabled() {
            return !vm.funnyPictureText.$valid;
        }
        var vm = this;
        vm.picture = {}, vm.headerText = "", vm.footerText = "", vm.funnyPicture = {}, vm.funniesByTemplate = {}, 
        vm.totalItems = 0, vm.currentPage = 1, vm.itemsPerPage = 6, vm.progress = !1, vm.loaded = !1, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, vm.createFunnyPicture = createFunnyPicture, 
        vm.createNew = createNew, vm.cancel = cancel, vm.isButtonDisabled = isButtonDisabled, 
        activate();
    }
    angular.module("app").controller("CreateFunnyController", CreateFunnyController), 
    CreateFunnyController.$inject = [ "$routeParams", "values", "PicturesFactory", "FunniesFactory", "FunnyThumbnailsByPicture" ];
}(), function() {
    "use strict";
    function PreviewFunnyController($window, $location, $routeParams, values, FunniesFactory, FunnyThumbnailsByPicture) {
        function activate() {
            FunniesFactory.get({
                id: $routeParams.funnyPictureId
            }, function(funnyPicture) {
                vm.funnyPicture = funnyPicture, pageChanged();
            }, function() {});
        }
        function pageChanged() {
            FunnyThumbnailsByPicture.query({
                id: vm.funnyPicture.template.id,
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funniesByTemplate = data.entities, vm.totalItems = data.count;
            }, function() {});
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        function swapFunnyPicture(funnyThumbnail) {
            $location.path("/preview/" + funnyThumbnail.funnyPictureId, !1), vm.currentLocation = currentUrl + funnyThumbnail.funnyPictureId, 
            FunniesFactory.get({
                id: funnyThumbnail.funnyPictureId
            }, function(funnyPicture) {
                vm.funnyPicture = funnyPicture;
            }, function() {});
        }
        function shareSocial(baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent(vm.currentLocation);
            event.preventDefault(), $window.open(url, "_blank", "width=" + width + ",height=" + height);
        }
        var vm = this, currentUrl = $location.absUrl().split("#")[0] + "#/preview/";
        vm.funnyPicture = {}, vm.funniesByTemplate = {}, vm.totalItems = 0, vm.currentPage = 1, 
        vm.itemsPerPage = 6, vm.currentLocation = vm.currentUrl + $routeParams.funnyPictureId, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, vm.swapFunnyPicture = swapFunnyPicture, 
        vm.shareSocial = shareSocial, activate();
    }
    angular.module("app").controller("PreviewFunnyController", PreviewFunnyController), 
    PreviewFunnyController.$inject = [ "$window", "$location", "$routeParams", "values", "FunniesFactory", "FunnyThumbnailsByPicture" ];
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
    angular.module("app").factory("PicturesFactory", PicturesFactory), PicturesFactory.$inject = [ "$resource", "constants" ];
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
    angular.module("app").factory("PictureThumbnailsFactory", PictureThumbnailsFactory), 
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
    angular.module("app").service("FileUploadService", FileUploadService), FileUploadService.$inject = [ "$http", "constants" ];
}(), function() {
    "use strict";
    function PicturesController(values, PictureThumbnailsFactory) {
        function pageChanged() {
            PictureThumbnailsFactory.query({
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.pictureThumbnails = data.entities, vm.totalItems = data.count;
            }, function() {});
        }
        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
        var vm = this;
        vm.pictureThumbnails = {}, vm.totalItems = 0, vm.currentPage = 1, vm.itemsPerPage = values.itemsPerPage, 
        vm.pageChanged = pageChanged, vm.showPagination = showPagination, pageChanged();
    }
    angular.module("app").controller("PicturesController", PicturesController), PicturesController.$inject = [ "values", "PictureThumbnailsFactory" ];
}(), function() {
    "use strict";
    function CreatePictureController($location, $mdToast, FileUploadService, PicturesFactory) {
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
                        vm.progress = !1, vm.loaded = !0, $mdToast.show($mdToast.simple().content("File " + data.name + " uploaded to server!").position("bottom left").hideDelay(5e3)), 
                        $location.path("/createFunnyPicture/" + data.id);
                    }, function() {
                        vm.progress = !1;
                    });
                });
            } else vm.progress = !1;
        }
        function isButtonDisabled() {
            return !vm.templateInputForm.$valid;
        }
        var vm = this;
        vm.pictureTitle = "", vm.pictureUrl = "", vm.progress = !1, vm.loaded = !1, vm.isButtonDisabled = isButtonDisabled, 
        vm.uploadFile = uploadFile;
    }
    angular.module("app").controller("CreatePictureController", CreatePictureController), 
    CreatePictureController.$inject = [ "$location", "$mdToast", "FileUploadService", "PicturesFactory" ];
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
    angular.module("app").factory("FeedbacksFactory", FeedbacksFactory), FeedbacksFactory.$inject = [ "$resource", "constants" ];
}(), function() {
    "use strict";
    function ContactController($mdToast, FeedbacksFactory) {
        function sendFeedback() {
            FeedbacksFactory.save(vm.feedback, function() {
                $mdToast.show($mdToast.simple().content("Thank you " + vm.feedback.name + ", your feedback was sent.").position("bottom left").hideDelay(5e3));
            }, function() {});
        }
        function isButtonDisabled() {
            return !vm.contactForm.$valid;
        }
        var vm = this;
        vm.feedback = {}, vm.sendFeedback = sendFeedback, vm.isButtonDisabled = isButtonDisabled;
    }
    angular.module("app").controller("ContactController", ContactController), ContactController.$inject = [ "$mdToast", "FeedbacksFactory" ];
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
            NEXT_LABEL: "Next"
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
            HOME_HEADER: "Головна",
            TEMPLATES_HEADER: "Шаблони",
            CREATE_TEMPLATE_HEADER: "Створити шаблон",
            CONTACT_US_HEADER: "Контакти",
            COMMENTS_FALLBACK_1: "Будь ласка, включіть JavaScript для перегляду",
            COMMENTS_FALLBACK_2: "Disqus коментарів.",
            PREVIOUS_LABEL: "Попередня",
            NEXT_LABEL: "Наступна"
        }), $translateProvider.determinePreferredLanguage(), $translateProvider.fallbackLanguage("en_US");
    }
    angular.module("app").config(translation), translation.$inject = [ "$translateProvider" ];
}();