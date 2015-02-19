(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('constants', {
            'apiUrl': 'http://localhost:8080/funny-pictures-rest-api/api'
        });
})();
