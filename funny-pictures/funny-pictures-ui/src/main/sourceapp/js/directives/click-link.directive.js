(function() {
    'use strict';

    angular
        .module('app')
        .directive('clickLink', clickLink);

    clickLink
        .$inject = [
            '$location'
        ];

    function clickLink($location) {
        return {
            link: function(scope, element, attrs) {
                element.on('click', function() {
                    scope.$apply(function() {
                        $location.path(attrs.clickLink);
                    });
                });
            }
        }
    }

})();
