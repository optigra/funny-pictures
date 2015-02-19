(function () {
    'use strict';

    angular
        .module('app')
        .factory('PicturesFactory', PicturesFactory);

    PicturesFactory
        .$inject = [
        '$resource',
        'constants'
    ];

    function PicturesFactory($resource, constants) {
        return $resource(constants.apiUrl + '/pictures/:id', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
