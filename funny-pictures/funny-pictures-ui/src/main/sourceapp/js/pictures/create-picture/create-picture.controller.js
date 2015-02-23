(function () {
    'use strict';

    angular
        .module('app.pictures')
        .controller('CreatePictureController', CreatePictureController);

    CreatePictureController
        .$inject = [
        '$location',
        '$exceptionHandler',
        'logger',
        'FileUploadService',
        'PicturesFactory'
    ];

    function CreatePictureController($location, $exceptionHandler, logger, FileUploadService, PicturesFactory) {
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
                                logger.success('File ' + data.name + ' uploaded to server.');
                                $location.path('/createFunnyPicture/' + data.id);
                            },
                            function (e) {
                                vm.progress = false;
                                $exceptionHandler(e);
                            }
                        );
                    }
                );
            } else {
                vm.progress = false;
                $exceptionHandler("Bad url or file");
            }
        }

        function isButtonDisabled() {
            return !vm.templateInputForm.$valid;
        }
    }

})();
