var funnyPicturesApp = angular.module("mainModule", [ "ngResource", "ui.bootstrap", "ngRoute", "funnyControllers" ]);

funnyPicturesApp.factory("Pictures", function(a, b) {
    return a(b.getApiUrl() + "/pictures/:id", {}, {
        query: {
            method: "GET",
            isArray: !1
        }
    });
}), funnyPicturesApp.factory("PicturesThumbnails", function(a, b) {
    return a(b.getApiUrl() + "/picturesthumb/:id", {}, {
        query: {
            method: "GET",
            isArray: !1
        }
    });
}), funnyPicturesApp.factory("Funnies", function(a, b) {
    return a(b.getApiUrl() + "/funnies/:id", {}, {
        query: {
            method: "GET",
            isArray: !1
        }
    });
}), funnyPicturesApp.factory("FunnyPicturesThumbnails", function(a, b) {
    return a(b.getApiUrl() + "/funniesthumb/:id", {}, {
        query: {
            method: "GET",
            isArray: !1
        }
    });
}), funnyPicturesApp.factory("Feedback", function(a, b) {
    return a(b.getApiUrl() + "/feedbacks/:id", {}, {
        query: {
            method: "GET",
            isArray: !1
        }
    });
}), funnyPicturesApp.service("FileUpload", [ "$http", function(a) {
    function b(a) {
        return a.data.message;
    }
    function c(a) {
        return a.data;
    }
    this.uploadFileToUrl = function(d, e) {
        var f = new FormData();
        f.append("content", d);
        var g = a.post(e, f, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": void 0
            }
        });
        return g.then(c, b);
    };
} ]), funnyPicturesApp.config([ "$routeProvider", function(a) {
    a.when("/home", {
        templateUrl: "html/home.html",
        controller: "HomeController"
    }).when("/createTemplate", {
        templateUrl: "html/createPicture.html",
        controller: "CreatePictureController"
    }).when("/createFunnyPicture", {
        templateUrl: "html/createFunnyPicture.html",
        controller: "CreateFunnyPictureController"
    }).when("/preview", {
        templateUrl: "html/funnyPicturePreview.html",
        controller: "PreviewFunnyController"
    }).when("/funnies", {
        templateUrl: "html/funnies.html",
        controller: "FunniesController"
    }).when("/contact", {
        templateUrl: "html/contact.html",
        controller: "ContactController"
    }).otherwise({
        redirectTo: "/home"
    });
} ]), funnyPicturesApp.directive("fileModel", [ "$parse", function(a) {
    return {
        restrict: "A",
        link: function(b, c, d) {
            var e = a(d.fileModel), f = e.assign;
            c.bind("change", function() {
                var a = new FileReader();
                a.onload = function(a) {
                    $("#imagePreview").attr("src", a.target.result);
                }, a.readAsDataURL(c[0].files[0]), b.$apply(function() {
                    f(b, c[0].files[0]);
                });
            });
        }
    };
} ]), funnyPicturesApp.directive("header", function() {
    return {
        restrict: "E",
        templateUrl: "html/directives/header.html"
    };
}), funnyPicturesApp.directive("footer", function() {
    return {
        restrict: "E",
        templateUrl: "html/directives/footer.html"
    };
}), funnyPicturesApp.service("SharedProperties", function() {
    var a = {}, b = "http://localhost:8080/funny-pictures-rest-api/api", c = 0;
    return {
        getApiUrl: function() {
            return b;
        },
        getGeneratedFunny: function() {
            return a;
        },
        setGeneratedFunny: function(b) {
            a = b;
        },
        setTemplateId: function(a) {
            c = a;
        },
        getTemplateId: function() {
            return c;
        }
    };
});