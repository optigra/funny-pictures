// Include in index.html so that app level exceptions are handled.
// Exclude from testRunner.html which should run exactly what it wants to run
(function () {
    'use strict';

    angular
        .module('blocks.exception')
        .factory('$exceptionHandler', extendExceptionHandler);

    extendExceptionHandler
        .$inject = [
        '$injector'
    ];

    function extendExceptionHandler($injector) {
        return function (exception) {
            var logger = $injector.get('logger');
            logger.error(exception.statusText, exception.stack);

        };
    }

})();
