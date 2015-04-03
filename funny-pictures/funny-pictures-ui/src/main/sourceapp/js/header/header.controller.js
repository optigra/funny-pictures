(function() {
    'use strict';

    angular
        .module('app.header')
        .controller('HeaderController', HeaderController);

    HeaderController
        .$inject = [
            '$location',
            '$mdSidenav'
        ];

    function HeaderController($location, $mdSidenav) {
        var vm = this;

        vm.isActive = isActive;
        vm.toggleMenu = toggleMenu;

        function isActive(viewLocation) {
            return viewLocation === $location.path();
        }
        function toggleMenu() {
            $mdSidenav('left').toggle();
        }
    }

})();
