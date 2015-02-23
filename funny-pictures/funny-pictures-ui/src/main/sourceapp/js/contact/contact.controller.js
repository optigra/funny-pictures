(function() {
    'use strict';

    angular
        .module('app.contact')
        .controller('ContactController', ContactController);

    ContactController
        .$inject = [
            '$exceptionHandler',
            'logger',
            'FeedbacksFactory'
        ];

    function ContactController($exceptionHandler,logger, FeedbacksFactory) {
        var vm = this;

        vm.feedback = {};

        vm.sendFeedback = sendFeedback;
        vm.isButtonDisabled = isButtonDisabled;

        function sendFeedback() {
            FeedbacksFactory.save(vm.feedback,
                function() {
                    vm.contactForm.$rollbackViewValue();
                    logger.success('Thank you ' + vm.feedback.name + ', your feedback was sent.');
                },
                function(e) {
                    $exceptionHandler(e);
                }
            );
        }

        function isButtonDisabled() {
            return !vm.contactForm.$valid;
        }
    }

})();
