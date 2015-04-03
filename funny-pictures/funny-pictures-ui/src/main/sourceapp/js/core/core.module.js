(function () {
    'use strict';

    angular
        .module('app.core', [
            'ngResource',
            'ngRoute',

            'ngMaterial',
            'ngClipboard',
            'pascalprecht.translate',
            'wu.masonry',
            'ui.bootstrap',

            'blocks.exception',
            'blocks.logger'
        ]);

})();