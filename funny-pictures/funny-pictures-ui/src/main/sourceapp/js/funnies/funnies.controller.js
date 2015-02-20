(function() {
    'use strict';

    angular
        .module('app')
        .controller('FunniesController', FunniesController);

    FunniesController
        .$inject = [
            'values',
            'FunnyThumbnailsFactory'
        ];

    function FunniesController(values, FunnyThumbnailsFactory) {
        var vm = this;

        vm.funnyThumbnails = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = values.itemsPerPage

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
            });
        }

        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
    }

})();
