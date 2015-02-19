(function() {
    'use strict';

    angular
        .module('app')
        .controller('PicturesController', PicturesController);

    PicturesController
        .$inject = [
            'values',
            'PictureThumbnailsFactory'
        ];

    function PicturesController(values, PictureThumbnailsFactory) {
        var vm = this;

        vm.pictureThumbnails = {};
        vm.totalItems = 0;
        vm.currentPage = 1;
        vm.itemsPerPage = values.itemsPerPage

        vm.pageChanged = pageChanged;
        vm.showPagination = showPagination;

        pageChanged();

        function pageChanged() {
            PictureThumbnailsFactory.query({
                offset: (vm.currentPage - 1) * vm.itemsPerPage,
                limit: vm.itemsPerPage,
                thumbnailType: values.thumbnailType
            }, function(data) {
                vm.pictureThumbnails = data.entities;
                vm.totalItems = data.count;
            }, function(error) {
                // ToDo
            });
        }

        function showPagination() {
            return vm.totalItems > vm.itemsPerPage;
        }
    }

})();
