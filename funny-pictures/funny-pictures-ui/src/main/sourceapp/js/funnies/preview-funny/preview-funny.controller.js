(function() {
    'use strict';

    angular
        .module('app.funnies')
        .controller('PreviewFunnyController', PreviewFunnyController);

    PreviewFunnyController
        .$inject = [
            '$window',
            '$location',
            '$routeParams',
            '$exceptionHandler',
            'values',
            'FunniesFactory',
            'FunnyThumbnailsByPicture'
        ];

    function PreviewFunnyController($window, $location, $routeParams, $exceptionHandler, values, FunniesFactory, FunnyThumbnailsByPicture) {
        var vm = this;
        var currentUrl = $location.absUrl().split('#')[0] + '#/preview/';

        vm.funnyPicture = {};
        vm.funniesByTemplate = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = 6; // Move to values
        vm.currentLocation = vm.currentUrl + $routeParams.funnyPictureId;

        vm.pageChanged = pageChanged;
        vm.showPagination = showPagination;
        vm.swapFunnyPicture = swapFunnyPicture;
        vm.shareSocial = shareSocial;

        activate();

        function activate() {
            FunniesFactory.get({
                id: $routeParams.funnyPictureId
            }, function(funnyPicture) {
                vm.funnyPicture = funnyPicture;
                pageChanged();
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function pageChanged() {
            FunnyThumbnailsByPicture.query({
                id: vm.funnyPicture.template.id,
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funniesByTemplate = data.entities;
                vm.totalItems = data.count;
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

            FunniesFactory.get({
                id: funnyThumbnail.funnyPictureId
            }, function(funnyPicture) {
                vm.funnyPicture = funnyPicture;
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function shareSocial(baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent(vm.currentLocation);
            event.preventDefault();
            $window.open(url, "_blank", "width=" + width + ",height=" + height);
        }
    }

})();
