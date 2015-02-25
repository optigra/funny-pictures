(function () {
    'use strict';

    angular
        .module('app.core')
        .config(configure);

    configure
        .$inject = [
            '$logProvider',
            'exceptionHandlerProvider'
        ];

    function configure($logProvider, exceptionHandlerProvider) {
        if ($logProvider.debugEnabled) {
            $logProvider.debugEnabled(true);
        }
        exceptionHandlerProvider.configure('test-');
    }

})();
