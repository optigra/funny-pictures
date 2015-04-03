(function() {
    'use strict';

    angular
        .module('app.footer')
        .controller('FooterController', FooterController);

    FooterController
        .$inject = [
        '$translate'
    ];

    function FooterController($translate) {
        var vm = this;

        vm.changeLanguage = changeLanguage;
        vm.getCurrentLanguage = getCurrentLanguage;

        function changeLanguage(langKey) {
            $translate.use(langKey);
        }
        function getCurrentLanguage() {
            return $translate.use();
        }
    }

})();
