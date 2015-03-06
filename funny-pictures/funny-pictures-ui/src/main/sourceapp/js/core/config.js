(function () {
    'use strict';

    angular
        .module('app.core')
        .config(configure);

    configure
        .$inject = [
            '$logProvider',
            'ngClipProvider'
        ];

    function configure($logProvider, ngClipProvider) {
        if ($logProvider.debugEnabled) {
            $logProvider.debugEnabled(true);
        }
        ngClipProvider.setPath('js/ZeroClipboard.swf');
    }

})();
