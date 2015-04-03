(function() {
    'use strict';

    angular
        .module('app.authorize')
        .controller('AuthorizeController', AuthorizeController);

    AuthorizeController
        .$inject = [
        '$resource',
        '$exceptionHandler',
        'logger'
    ];

    function AuthorizeController($resource, $exceptionHandler, logger) {
        var vm = this;

        vm.username = '';
        vm.password = '';

        vm.authorize = authorize;
        vm.isButtonDisabled = isButtonDisabled;

        function authorize() {
            vm.progress = true;

        }

        function isButtonDisabled() {
            return !vm.authorizeForm.$valid;
        }
    }

})();
