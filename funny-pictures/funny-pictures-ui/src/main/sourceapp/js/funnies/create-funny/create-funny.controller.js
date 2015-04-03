(function () {
    'use strict';

    angular
        .module('app.funnies')
        .controller('CreateFunnyController', CreateFunnyController);

    CreateFunnyController
        .$inject = [
        '$scope',
        '$window',
        '$routeParams',
        '$location',
        '$exceptionHandler',
        'logger',
        'values',
        'PicturesFactory',
        'FunniesFactory',
        'FunnyThumbnailsByPictureFactory',
        'TagsFactory'
    ];

    function CreateFunnyController($scope, $window, $routeParams, $location, $exceptionHandler, logger, values, PicturesFactory, FunniesFactory, FunnyThumbnailsByPictureFactory, TagsFactory) {
        var vm = this;
        var currentUrl = $location.absUrl().split('#')[0] + '#/preview/';

        vm.picture = {};
        vm.headerText = '';
        vm.footerText = '';
        vm.funnyPicture = {};
        vm.tags = ['funny', 'meme', 'lol'];
        vm.tag = '';
        vm.funniesByTemplate = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = 6; // Move to values
        vm.progress = false;
        vm.loaded = false;
        vm.currentFunnyLocation = currentUrl;

        vm.pageChanged = pageChanged;
        vm.showPagination = showPagination;
        vm.createFunnyPicture = createFunnyPicture;
        vm.createNew = createNew;
        vm.cancel = cancel;
        vm.isButtonDisabled = isButtonDisabled;
        vm.shareSocial = shareSocial;
        vm.clipCopyMessage = clipCopyMessage;
        vm.createTag = createTag;

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

            TagsFactory.query({}, function (data) {
                vm.tags = data;
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function pageChanged() {
            FunnyThumbnailsByPictureFactory.query({
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
            postObject.tags = vm.tags;
            FunniesFactory.save(postObject,
                function (data) {
                    vm.funnyPicture = data;
                    vm.progress = false;
                    vm.loaded = true;
                    vm.currentFunnyLocation = currentUrl + vm.funnyPicture.id;
                    pageChanged();
                }, function (e) {
                    vm.progress = false;
                    $exceptionHandler(e);
                });
        }

        function createTag() {
            var postObject = new Object();
            postObject.name = vm.tag;
            TagsFactory.save(postObject,
                function (data) {
                    vm.tags = data;
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

        function shareSocial(baseUrl, width, height) {
            var url = baseUrl + encodeURIComponent(vm.currentFunnyLocation);
            event.preventDefault();
            $window.open(url, "_blank", "width=" + width + ",height=" + height);
        }

        function clipCopyMessage() {
            logger.info("Link is copied to clipboard")
        }
    }

})();
