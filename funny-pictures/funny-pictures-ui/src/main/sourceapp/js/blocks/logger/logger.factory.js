(function () {
    'use strict';

    angular
        .module('blocks.logger')
        .factory('logger', logger);

    logger
        .$inject = [
            '$log',
            '$mdToast'
        ];

    function logger($log, $mdToast) {
        var position = 'bottom left',
            hideDelay = 5000;

        var service = {
            showToasts: true,

            error: error,
            info: info,
            success: success,
            warning: warning,

            // straight to console; bypass $mdToast
            log: $log.log
        };

        return service;
        /////////////////////

        function error(message, data) {
            $mdToast.show(
                $mdToast.simple()
                    .content('Error: ' + message)
                    .position(position)
                    .hideDelay(hideDelay)
            );
            $log.error('Error: ' + message, data);
        }

        function info(message, data) {
            $mdToast.show(
                $mdToast.simple()
                    .content('Info: ' + message)
                    .position(position)
                    .hideDelay(hideDelay)
            );
            $log.info('Info: ' + message, data);
        }

        function success(message, data) {
            $mdToast.show(
                $mdToast.simple()
                    .content('Success: ' + message)
                    .position(position)
                    .hideDelay(hideDelay)
            );
            $log.info('Success: ' + message, data);
        }

        function warning(message, data) {
            $mdToast.show(
                $mdToast.simple()
                    .content('Warning: ' + message)
                    .position(position)
                    .hideDelay(hideDelay)
            );
            $log.warn('Warning: ' + message, data);
        }
    }

}());
