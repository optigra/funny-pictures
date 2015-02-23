(function() {
    'use strict';

    angular
        .module('app.header')
        .controller('HeaderController', HeaderController);

    HeaderController
        .$inject = [
            '$location',
            '$mdSidenav',
            '$translate'
        ];

    function HeaderController($location, $mdSidenav, $translate) {
        var vm = this;

        vm.isActive = isActive;
        vm.changeLanguage = changeLanguage;
        vm.getCurrentLanguage = getCurrentLanguage;
        vm.toggleMenu = toggleMenu;

        function isActive(viewLocation) {
            return viewLocation === $location.path();
        }
        function changeLanguage(langKey) {
            $translate.use(langKey);
        }
        function getCurrentLanguage() {
            return $translate.use();
        }
        function toggleMenu() {
            $mdSidenav('left').toggle();
        }
    }

})();
