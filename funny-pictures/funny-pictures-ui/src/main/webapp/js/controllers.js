/**
 * Created by rostyslav on 9/29/14.
 */
var funnyControllers = angular.module('funnyControllers', []);

funnyControllers.controller('HomeController', [ '$scope', '$http' , '$location', '$window', 'Pictures' , 'Funnies' , 'sharedProperties', function ($scope, $http, $location, $window, Pictures, Funnies, sharedProperties) {
    $scope.imagePreview = {};
    $scope.alerts = [];
    $scope.headerText = "";
    $scope.footerText = "";
    $scope.currentPage = 0;
    $scope.totalItems = 11;
    $scope.itemsPerPage = 6;
    $scope.generatedImage = sharedProperties.getGeneratedFunny();

    Pictures.query({
        offset: 0,
        limit: 20
    }, function (data) {
        $scope.pictures = data.entities;
        $scope.totalItems = data.count;
        $scope.imagePreview = data.entities[0];
    }, function (error) {
        $scope.totalItems = 0;
        $scope.alerts.push({type: 'danger', msg: error.statusText + " " + error.status });
    });

    $scope.pageChanged = function () {
        console.log('Page changed to: ' + $scope.currentPage);
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
                sharedProperties.setGeneratedFunny(data);
                $scope.alerts.push({type: 'success', msg: "Picture created with id : " + data.id});
                $location.path('/preview');
            },
            function (error) {
                $scope.alerts.push({type: 'danger', msg: "Can't create picture" + error.statusText + " " + error.status });
            });


    };

} ]);
