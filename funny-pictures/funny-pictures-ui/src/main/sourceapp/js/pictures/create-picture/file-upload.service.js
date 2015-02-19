(function () {
    'use strict';

    angular
        .module('app')
        .service('FileUploadService', FileUploadService);

    FileUploadService
        .$inject = [
        '$http',
        'constants'
    ];

    function FileUploadService($http, constants) {
        var uploadUrl = constants.apiUrl + "/content";

        this.uploadFileToUrl = function (file) {
            var fd = new FormData();
            fd.append('content', file);
            return $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {
                    'content': 'file',
                    'Content-Type': undefined
                }
            });
        };

        this.uploadFileUrlToUrl = function (urlToFile) {
            return $http({
                url: uploadUrl,
                method: "POST",
                headers: {
                    'content': 'url'
                },
                params: {
                    url: urlToFile
                }
            });
        }
    }

})();
