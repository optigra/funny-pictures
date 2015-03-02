(function() {
    'use strict';

    angular
        .module('app.funnies')
        .factory('FunnyThumbnailsByPictureFactory', FunnyThumbnailsByPictureFactory);

    FunnyThumbnailsByPictureFactory
        .$inject = [
            '$resource',
            'constants'
        ];

    function FunnyThumbnailsByPictureFactory($resource, constants) {
        return $resource(constants.apiUrl + '/funnies/:id/funniesThumbs', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
