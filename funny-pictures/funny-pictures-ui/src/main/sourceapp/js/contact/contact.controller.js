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
        vm.progress = false;

        vm.sendFeedback = sendFeedback;
        vm.isButtonDisabled = isButtonDisabled;

        function sendFeedback() {
            vm.progress = true;
            FeedbacksFactory.save(vm.feedback,
                function() {
                    vm.progress = false;
                    logger.success('Thank you ' + vm.feedback.name + ', your feedback was sent.');
                    vm.feedback = {};
                },
                function(e) {
                    vm.progress = false;
                    $exceptionHandler(e);
                }
            );
        }

        function isButtonDisabled() {
            return !vm.contactForm.$valid;
        }
    }

})();
