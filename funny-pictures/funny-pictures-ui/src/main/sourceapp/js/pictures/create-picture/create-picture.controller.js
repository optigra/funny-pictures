(function () {
    'use strict';

    angular
        .module('app')
        .controller('CreatePictureController', CreatePictureController);

    CreatePictureController
        .$inject = [
        '$location',
        '$mdToast',
        'FileUploadService',
        'PicturesFactory'
    ];

    function CreatePictureController($location, $mdToast, FileUploadService, PicturesFactory) {
        var vm = this;

        vm.pictureTitle = "";
        vm.pictureUrl = "";
        vm.progress = false;
        vm.loaded = false;

        vm.isButtonDisabled = isButtonDisabled;
        vm.uploadFile = uploadFile;

        function uploadFile() {
            var file = vm.myFile;
            var urlToFile = vm.pictureUrl;
            vm.progress = true;

            if (file || urlToFile) {
                var promiseFile = {};
                if (file) {
                    promiseFile = FileUploadService.uploadFileToUrl(file);
                } else if (urlToFile) {
                    promiseFile = FileUploadService.uploadFileUrlToUrl(urlToFile);
                }
                promiseFile.then(
                    function (response) {
                        var pictureObject = {
                            name: vm.pictureTitle,
                            url: response.data.path
                        };
                        PicturesFactory.save(pictureObject,
                            function (data) {
                                vm.progress = false;
                                vm.loaded = true;
                                $mdToast.show(
                                    $mdToast.simple()
                                        .content('File ' + data.name + ' uploaded to server!')
                                        .position('bottom left')
                                        .hideDelay(5000)
                                );
                                $location.path('/createFunnyPicture/' + data.id);
                            },
                            function (error) {
                                vm.progress = false;
                                // ToDo
                            }
                        );
                    }
                );
            } else {
                vm.progress = false;
                // ToDo
            }
        }

        function isButtonDisabled() {
            return !vm.templateInputForm.$valid;
        }
    }

})();
