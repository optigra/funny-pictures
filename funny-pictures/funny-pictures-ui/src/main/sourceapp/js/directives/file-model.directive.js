(function() {
    'use strict';

    angular
        .module('app')
        .directive('fileModel', fileModel);

    fileModel
        .$inject = [
        '$parse'
    ];

    function fileModel($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function() {
                    var reader = new FileReader();

                    reader.onload = function(e) {
                        var image = $("#image-preview");
                        image.fadeOut('fast', function() {
                            image.attr('src', e.target.result);
                            image.removeClass('hidden');
                            image.fadeIn('fast');
                        });
                    };
                    reader.readAsDataURL(element[0].files[0]);

                    scope.$apply(function() {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }

})();
