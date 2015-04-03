(function() {
    'use strict';

    angular
        .module('app.footer')
        .directive('footer', footer);

    function footer() {
        return {
            restrict: 'E',
            templateUrl: "html/directives/footer.html"
        }
    }

})();
