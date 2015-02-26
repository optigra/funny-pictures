(function () {
    'use strict';

    angular
        .module('app.funnies')
        .controller('CreateFunnyController', CreateFunnyController);

    CreateFunnyController
        .$inject = [
        '$scope',
        '$routeParams',
        '$exceptionHandler',
        'logger',
        'values',
        'PicturesFactory',
        'FunniesFactory',
        'FunnyThumbnailsByPicture'
    ];

    function CreateFunnyController($scope, $routeParams, $exceptionHandler, logger, values, PicturesFactory, FunniesFactory, FunnyThumbnailsByPicture) {
        var vm = this;

        vm.picture = {};
        vm.headerText = '';
        vm.footerText = '';
        vm.funnyPicture = {};
        vm.funniesByTemplate = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = 6; // Move to values
        vm.progress = false;
        vm.loaded = false;

        vm.pageChanged = pageChanged;
        vm.showPagination = showPagination;
        vm.createFunnyPicture = createFunnyPicture;
        vm.createNew = createNew;
        vm.cancel = cancel;
        vm.isButtonDisabled = isButtonDisabled;

        activate();

        function activate() {
            PicturesFactory.get({
                id: $routeParams.pictureId
            }, function (picture) {
                vm.picture = picture;
                pageChanged();
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function pageChanged() {
            FunnyThumbnailsByPicture.query({
                id: vm.picture.id,
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function (data) {
                vm.funniesByTemplate = data.entities;
                vm.totalItems = data.count;
                $scope.$broadcast('dataloaded');
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function createFunnyPicture() {
            vm.progress = true;
            var postObject = new Object();
            postObject.name = vm.picture.name;
            postObject.header = vm.headerText;
            postObject.footer = vm.footerText;
            postObject.template = {};
            postObject.template.id = vm.picture.id;
            FunniesFactory.save(postObject,
                function (data) {
                    vm.funnyPicture = data;
                    vm.progress = false;
                    vm.loaded = true;
                    pageChanged();
                }, function (e) {
                    $exceptionHandler(e);
                });
        }

        function createNew() {
            vm.headerText = '';
            vm.footerText = '';
            vm.loaded = false;
            vm.funnyPicture = {};
        }

        function cancel() {
            FunniesFactory.delete({
                id: vm.funnyPicture.id
            }, function () {
                vm.loaded = false;
                vm.funnyPicture = {};
                vm.pageChanged();
                logger.info('Deleted Funny picture');
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }

        function isButtonDisabled() {
            return !vm.funnyPictureText.$valid;
        }
    }

})();
