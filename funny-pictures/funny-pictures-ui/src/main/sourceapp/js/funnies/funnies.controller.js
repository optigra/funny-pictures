(function() {
    'use strict';

    angular
        .module('app.funnies')
        .controller('FunniesController', FunniesController);

    FunniesController
        .$inject = [
            '$exceptionHandler',
            'values',
            'FunnyThumbnailsFactory'
        ];

    function FunniesController($exceptionHandler, values, FunnyThumbnailsFactory) {
        var vm = this;

        vm.funnyThumbnails = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = values.itemsPerPage;

        vm.pageChanged = pageChanged;
        vm.showPagination = showPagination;

        pageChanged();

        function pageChanged() {
            FunnyThumbnailsFactory.query({
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.funnyThumbnails = data.entities;
                vm.totalItems = data.count;
            }, function (e) {
                $exceptionHandler(e);
            });
        }

        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
    }

})();
