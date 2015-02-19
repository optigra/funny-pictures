(function() {
    'use strict';

    angular
        .module('app.core')
        .value('values', {
            'thumbnailType': 'MEDIUM',
            'itemsPerPage': getItemsPerPage()
        });

    function getItemsPerPage($window) {
        var itemsPerPage = 15;

        if (window.innerWidth >= 960 && window.innerWidth < 1200) {
            itemsPerPage = 25;
        } else if (window.innerWidth >= 1200) {
            itemsPerPage = 30;
        }
        return itemsPerPage;
    }

})();
