(function() {
    'use strict';

    angular
        .module('app')
        .directive('reloadDisqus', reloadDisqus);

    reloadDisqus
        .$inject = [
        '$timeout',
        '$location'
    ];

    function reloadDisqus($timeout, $location) {
        return {
            link: function($scope, element, attrs) {
                $scope.$on('dataloaded', function() {
                    $timeout(function () {
                        DISQUS.reset({
                            reload: true,
                            config: function () {
                                this.page.identifier = attrs.reloadDisqus;
                                this.page.url = $location.absUrl();
                            }
                        });
                    }, 0, false);
                });
            }
        }
    }

})();