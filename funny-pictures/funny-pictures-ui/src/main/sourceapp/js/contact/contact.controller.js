(function() {
    'use strict';

    angular
        .module('app')
        .controller('ContactController', ContactController);

    ContactController
        .$inject = [
            '$mdToast',
            'FeedbacksFactory'
        ];

    function ContactController($mdToast, FeedbacksFactory) {
        var vm = this;

        vm.feedback = {};

        vm.sendFeedback = sendFeedback;
        vm.isButtonDisabled = isButtonDisabled;

        function sendFeedback() {
            FeedbacksFactory.save(vm.feedback,
                function() {
                    $mdToast.show(
                        $mdToast.simple()
                        .content('Thank you ' + vm.feedback.name + ', your feedback was sent.')
                        .position('bottom left')
                        .hideDelay(5000)
                    );
                },
                function(error) {
                    // ToDo
                }
            );
        }

        function isButtonDisabled() {
            return !vm.contactForm.$valid;
        }
    }

})();
