(function() {
    'use strict';

    angular
        .module('app')
        .directive('header', header);

    function header() {
        return {
            restrict: 'E',
            templateUrl: "html/directives/header.html"
        }
    }

})();
