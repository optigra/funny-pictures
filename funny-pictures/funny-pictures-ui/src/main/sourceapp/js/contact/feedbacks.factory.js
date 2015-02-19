(function() {
    'use strict';

    angular
        .module('app')
        .factory('FeedbacksFactory', FeedbacksFactory);

    FeedbacksFactory
        .$inject = [
            '$resource',
            'constants'
        ];

    function FeedbacksFactory($resource, constants) {
        return $resource(constants.apiUrl + '/feedbacks/:id', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
