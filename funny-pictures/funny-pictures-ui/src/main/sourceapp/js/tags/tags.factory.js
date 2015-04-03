(function () {
    'use strict';

    angular
        .module('app.tags')
        .factory('TagsFactory', TagsFactory);

    TagsFactory
        .$inject = [
        '$resource',
        'constants'
    ];

    function TagsFactory($resource, constants) {
        return $resource(constants.apiUrl + '/tags/:id', {}, {
            'query': {
                method: 'GET',
                isArray: true
            }
        });
    }

})();
