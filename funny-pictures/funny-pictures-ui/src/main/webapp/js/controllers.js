var funnyControllers = angular.module("funnyControllers", []);

funnyControllers.controller("HomeController", [ "$scope", "$modal", "$location", "PicturesThumbnails", "Funnies", "SharedProperties", function(a, b, c, d, e, f) {
    a.headerText = "", a.footerText = "", a.currentPage = 1, a.totalItems = 0, a.itemsPerPage = 8, 
    a.thumbnailType = "BIG", d.query({
        offset: (a.currentPage - 1) * a.itemsPerPage,
        limit: a.itemsPerPage,
        thumbnailType: a.thumbnailType
    }, function(b) {
        a.pictureThumbnails = b.entities, a.totalItems = b.count;
    }, function() {
        a.totalItems = 0;
    }), a.pageChanged = function() {
        d.query({
            offset: (a.currentPage - 1) * a.itemsPerPage,
            limit: a.itemsPerPage,
            thumbnailType: a.thumbnailType
        }, function(b) {
            a.pictureThumbnails = b.entities, a.totalItems = b.count;
        }, function() {
            a.totalItems = 0;
        });
    }, a.showPagination = function() {
        return a.totalItems > a.itemsPerPage;
    }, a.createFunnyPicture = function(a) {
        f.setTemplateId(a), c.path("/createFunnyPicture");
    };
} ]), funnyControllers.controller("PreviewFunnyController", [ "$scope", "SharedProperties", function(a, b) {
    a.generatedImage = b.getGeneratedFunny();
} ]), funnyControllers.controller("HeaderController", [ "$scope", "$location", function(a, b) {
    a.isActive = function(a) {
        return a === b.path();
    };
} ]), funnyControllers.controller("FunniesController", [ "$scope", "$modal", "FunnyPicturesThumbnails", function(a, b, c) {
    a.funnies = {}, a.currentPage = 1, a.totalItems = 0, a.itemsPerPage = 8, a.thumbnailType = "BIG", 
    a.modalOpen = function(a) {
        b.open({
            templateUrl: "html/modal/funnyPreviewModal.html",
            controller: "FunnyPreviewModalController",
            resolve: {
                funnyThumbnail: function() {
                    return a;
                }
            }
        });
    }, c.query({
        offset: (a.currentPage - 1) * a.itemsPerPage,
        limit: a.itemsPerPage,
        thumbnailType: a.thumbnailType
    }, function(b) {
        a.funniesThumbnails = b.entities, a.totalItems = b.count;
    }, function() {
        a.totalItems = 0;
    }), a.pageChanged = function() {
        c.query({
            offset: (a.currentPage - 1) * a.itemsPerPage,
            limit: a.itemsPerPage,
            thumbnailType: a.thumbnailType
        }, function(b) {
            a.funniesThumbnails = b.entities, a.totalItems = b.count;
        }, function() {
            a.totalItems = 0;
        });
    }, a.showPagination = function() {
        return a.totalItems > a.itemsPerPage;
    };
} ]), funnyControllers.controller("FunnyPreviewModalController", [ "$scope", "$window", "$modalInstance", "funnyThumbnail", "Funnies", function(a, b, c, d, e) {
    var f = d.funnyPictureId;
    e.get({
        id: f
    }, function(b) {
        a.funnyPicture = b;
    }), a.cancel = function() {
        c.dismiss("cancel");
    }, a.fullSize = function() {
        b.open(a.funnyPicture.url);
    };
} ]), funnyControllers.controller("CreatePictureController", [ "$scope", "$location", "$window", "FileUpload", "SharedProperties", "Pictures", function(a, b, c, d, e, f) {
    a.pictureTitle = "", a.headerText = "", a.uploadFile = function() {
        var g = e.getApiUrl() + "/content", h = a.myFile;
        h ? d.uploadFileToUrl(h, g).then(function(c) {
            var d = {
                name: a.pictureTitle,
                url: c.path
            };
            f.save(d, function(c) {
                a.headerText = "File " + c + " uploaded to server!", b.path("/home");
            }, function(b) {
                a.headerText = "Error" + b.message;
            });
        }) : c.alert("Please select the file!");
    };
} ]), funnyControllers.controller("CreateFunnyPictureController", [ "$scope", "$window", "$modal", "$http", "SharedProperties", "Pictures", "Funnies", function(a, b, c, d, e, f, g) {
    a.template = {}, a.funniesByTemplate = {}, a.currentPage = 1, a.totalItems = 0, 
    a.itemsPerPage = 6, a.thumbnailType = "BIG", f.get({
        id: e.getTemplateId()
    }, function(b) {
        a.template = b, d({
            url: e.getApiUrl() + "/pictures/" + a.template.id + "/funniesThumb",
            method: "GET",
            params: {
                offset: (a.currentPage - 1) * a.itemsPerPage,
                limit: a.itemsPerPage,
                thumbnailType: a.thumbnailType
            }
        }).success(function(b) {
            a.funniesByTemplate = b.entities, a.totalItems = b.count;
        }).error(function(a) {
            console.log(a.statusText + " " + a.status);
        });
    }), a.createFunnyPicture = function() {
        var b = new Object();
        b.name = a.template.name, b.header = a.headerText, b.footer = a.footerText, b.template = {}, 
        b.template.id = a.template.id, g.save(b, function(b) {
            a.template = b;
        }, function(a) {
            console.log("Can't create picture" + a.statusText + " " + a.status);
        });
    }, a.pageChanged = function() {
        d({
            url: e.getApiUrl() + "/pictures/" + a.template.id + "/funnies",
            method: "GET",
            params: {
                offset: (a.currentPage - 1) * a.itemsPerPage,
                limit: a.itemsPerPage
            }
        }).success(function(b) {
            a.funniesByTemplate = b.entities, a.totalItems = b.count;
        }).error(function(a) {
            console.log(a.statusText + " " + a.status);
        });
    }, a.modalOpen = function(a) {
        console.log(a), c.open({
            templateUrl: "html/modal/funnyPreviewModal.html",
            controller: "FunnyPreviewModalController",
            resolve: {
                funnyThumbnail: function() {
                    return a;
                }
            }
        });
    }, a.isButtonDisabled = function() {
        return a.funnyPictureText.$valid ? !1 : !0;
    }, a.fullSize = function() {
        b.open(a.template.url);
    }, a.showPagination = function() {
        return a.totalItems > a.itemsPerPage;
    };
} ]), funnyControllers.controller("ContactController", [ "$scope", "Feedback", function(a, b) {
    a.alerts = [], a.feedback = {
        subject: "Select subject..."
    }, a.sendFeedback = function() {
        b.save(a.feedback, function(b, c) {
            a.alerts.push({
                type: "success",
                msg: "Thank you " + a.feedback.name + ", your feedback was sent."
            }), console.log(b + " " + c);
        }, function(a) {
            console.log("Feedback can't sent " + a.statusText + " " + a.status);
        });
    }, a.isButtonDisabled = function() {
        return a.contactForm.$valid ? !1 : !0;
    };
} ]);