(function() {
    'use strict';

    angular
        .module('app.funnies')
        .factory('FunnyThumbnailByFunnyPictureFactory', FunnyThumbnailByFunnyPictureFactory);

    FunnyThumbnailByFunnyPictureFactory
        .$inject = [
        '$resource',
        'constants'
    ];

    function FunnyThumbnailByFunnyPictureFactory($resource, constants) {
        return $resource(constants.apiUrl + '/funnies/:id/funnyThumb', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
