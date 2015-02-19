(function() {
    'use strict';

    angular
        .module('app')
        .directive('clickSelector', clickSelector);

    function clickSelector() {
        return {
            link: function(scope, element, attrs) {
                element.on('click', function() {
                    scope.$apply(function() {
                        $(attrs.clickSelector).click();
                    });
                });
            }
        }
    }

})();
