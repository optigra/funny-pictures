(function () {
    'use strict';

    angular
        .module('app.funnies')
        .controller('PreviewFunnyController', PreviewFunnyController);

    PreviewFunnyController
        .$inject = [
        '$scope',
        '$window',
        '$location',
        '$routeParams',
        'logger',
        '$exceptionHandler',
        'values',
        'FunniesFactory',
        'FunnyThumbnailByFunnyPictureFactory',
        'FunnyThumbnailsByPictureFactory'
    ];

    function PreviewFunnyController($scope, $window, $location, $routeParams, logger, $exceptionHandler, values, FunniesFactory, FunnyThumbnailByFunnyPictureFactory, FunnyThumbnailsByPictureFactory) {
        var vm = this;
        var currentUrl = $location.absUrl().split('#')[0] + '#/preview/';
        var thumbnailType = "BIG";

        vm.funnyPicture = {};
        vm.funnyThumbnail = {};
        vm.funniesByTemplate = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = 6; // Move to values
        vm.currentLocation = currentUrl + $routeParams.funnyPictureId;

        vm.pageChanged = pageChanged;
        vm.showPagination = showPagination;
        vm.swapFunnyPicture = swapFunnyPicture;
        vm.shareSocial = shareSocial;
        vm.clipCopyMessage = clipCopyMessage;

        activate();

        function activate() {
            FunnyThumbnailByFunnyPictureFactory.query({
                id: $routeParams.funnyPictureId,
                thumbnailType: thumbnailType
            }, function (funnyThumbnail) {
                vm.funnyThumbnail = funnyThumbnail;
                pageChanged();
                $scope.$broadcast('dataloaded');
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function pageChanged() {
            FunniesFactory.get({
                id: $routeParams.funnyPictureId
            }, function (data) {
                vm.funnyPicture = data;
                FunnyThumbnailsByPictureFactory.query({
                    id: vm.funnyPicture.template.id,
                    offset: (vm.currentPage - 1) * vm.itemsPerPage,
                    limit: vm.itemsPerPage,
                    thumbnailType: values.thumbnailType
                }, function (data) {
                    vm.funniesByTemplate = data.entities;
                    vm.totalItems = data.count;
                }, function (e) {
                    $exceptionHandler(e);
                });
            }, function (e) {
                $exceptionHandler(e);
            });

        }

        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }

        function swapFunnyPicture(funnyThumbnail) {
            $location.path('/preview/' + funnyThumbnail.funnyPictureId, false);
            vm.currentLocation = currentUrl + funnyThumbnail.funnyPictureId;

            FunnyThumbnailByFunnyPictureFactory.query({
                id: $routeParams.funnyPictureId,
                thumbnailType: thumbnailType
            }, function (funnyThumbnail) {
                vm.funnyThumbnail = funnyThumbnail;
            }, function (e) {
                $exceptionHandler(e);
            });

        }

        function shareSocial(baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent(vm.currentLocation);
            event.preventDefault();
            $window.open(url, "_blank", "width=" + width + ",height=" + height);
        }

        function clipCopyMessage() {
            logger.info("Link is copied to clipboard")
        }
    }

})();
