(function() {
    'use strict';

    angular
        .module('app')
        .controller('ContactController', ContactController);

    ContactController
        .$inject = [
            'logger',
            'FeedbacksFactory'
        ];

    function ContactController(logger, FeedbacksFactory) {
        var vm = this;

        vm.feedback = {};

        vm.sendFeedback = sendFeedback;
        vm.isButtonDisabled = isButtonDisabled;

        function sendFeedback() {
            FeedbacksFactory.save(vm.feedback,
                function() {
                    logger.success('Thank you ' + vm.feedback.name + ', your feedback was sent.');
                }
            );
        }

        function isButtonDisabled() {
            return !vm.contactForm.$valid;
        }
    }

})();
