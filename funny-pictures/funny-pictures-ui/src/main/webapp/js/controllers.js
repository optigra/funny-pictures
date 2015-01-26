/**
 * Created by rostyslav on 9/29/14.
 */
var funnyControllers = angular.module('funnyControllers', []);

funnyControllers.controller('HomeController', [
		'$scope',
		'$modal',
		'$location',
		'PicturesThumbnails',
		'Funnies',
		'SharedProperties',
		function($scope, $modal, $location, PicturesThumbnails, Funnies,
				SharedProperties) {
			$scope.headerText = "";
			$scope.footerText = "";
			$scope.currentPage = 1;
			$scope.totalItems = 0;
			$scope.itemsPerPage = 8;
			$scope.thumbnailType = "BIG";

			PicturesThumbnails.query({
				offset : ($scope.currentPage - 1) * $scope.itemsPerPage,
				limit : $scope.itemsPerPage,
				thumbnailType : $scope.thumbnailType
			}, function(data) {
				$scope.pictureThumbnails = data.entities;
				$scope.totalItems = data.count;
			}, function() {
				$scope.totalItems = 0;
			});

			$scope.pageChanged = function() {
				PicturesThumbnails.query({
					offset : ($scope.currentPage - 1) * $scope.itemsPerPage,
					limit : $scope.itemsPerPage,
					thumbnailType : $scope.thumbnailType
				}, function(data) {
					$scope.pictureThumbnails = data.entities;
					$scope.totalItems = data.count;
				}, function() {
					$scope.totalItems = 0;
				});
			};

			$scope.showPagination = function() {
				return $scope.totalItems > $scope.itemsPerPage;
			};

			$scope.createFunnyPicture = function(templateId) {
				SharedProperties.setTemplateId(templateId);
				$location.path('/createFunnyPicture');
			};

		} ]);

funnyControllers.controller('PreviewFunnyController', [ '$scope',
		'SharedProperties', function($scope, SharedProperties) {
			$scope.generatedImage = SharedProperties.getGeneratedFunny();
		} ]);

funnyControllers.controller('HeaderController', [ '$scope', '$location',
		function($scope, $location) {
			$scope.isActive = function(viewLocation) {
				return viewLocation === $location.path();
			};
		} ]);

funnyControllers.controller('FunniesController', [ '$scope', '$modal',
		'FunnyPicturesThumbnails',
		function($scope, $modal, FunnyPicturesThumbnails) {
			$scope.funnies = {};
			$scope.currentPage = 1;
			$scope.totalItems = 0;
			$scope.itemsPerPage = 8;
			$scope.thumbnailType = "BIG";

			$scope.modalOpen = function(funnyThumbnail) {
				var modalInstance = $modal.open({
					templateUrl : 'html/modal/funnyPreviewModal.html',
					controller : 'FunnyPreviewModalController',
					resolve : {
						funnyThumbnail : function() {
							return funnyThumbnail;
						}
					}
				});
			};

			//    Funnies.query({
			//        offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
			//        limit: $scope.itemsPerPage
			//    }, function (data) {
			//        $scope.funnies = data.entities;
			//        $scope.totalItems = data.count;
			//    }, function (error) {
			//        console.log(error.statusText + " " + error.status);
			//        $scope.alerts.push({type: 'danger', msg: error.statusText + " " + error.status});
			//    });
			//
			//    $scope.pageChanged = function () {
			//        Funnies.query({
			//            offset: ($scope.currentPage - 1) * $scope.itemsPerPage,
			//            limit: $scope.itemsPerPage
			//        }, function (data) {
			//            $scope.funnies = data.entities;
			//            $scope.totalItems = data.count;
			//        }, function (error) {
			//            console.log(error.statusText + " " + error.status);
			//        });
			//    };

			FunnyPicturesThumbnails.query({
				offset : ($scope.currentPage - 1) * $scope.itemsPerPage,
				limit : $scope.itemsPerPage,
				thumbnailType : $scope.thumbnailType
			}, function(data) {
				$scope.funniesThumbnails = data.entities;
				$scope.totalItems = data.count;
			}, function() {
				$scope.totalItems = 0;
			});

			$scope.pageChanged = function() {
				FunnyPicturesThumbnails.query({
					offset : ($scope.currentPage - 1) * $scope.itemsPerPage,
					limit : $scope.itemsPerPage,
					thumbnailType : $scope.thumbnailType
				}, function(data) {
					$scope.funniesThumbnails = data.entities;
					$scope.totalItems = data.count;
				}, function() {
					$scope.totalItems = 0;
				});
			};

			$scope.showPagination = function() {
				return $scope.totalItems > $scope.itemsPerPage;
			};
		} ]);

funnyControllers.controller('FunnyPreviewModalController', [ '$scope',
		'$window', '$modalInstance', 'funnyThumbnail', 'Funnies',
		function($scope, $window, $modalInstance, funnyThumbnail, Funnies) {
			var funnyPictureId = funnyThumbnail.funnyPictureId;
				Funnies.get({
					id : funnyPictureId
				}, function(funnyPicture) {
					$scope.funnyPicture = funnyPicture;
				});

			$scope.cancel = function() {
				$modalInstance.dismiss('cancel');
			};
			$scope.fullSize = function() {
				$window.open($scope.funnyPicture.url);
			}
		} ]);

funnyControllers.controller('CreatePictureController', [
		'$scope',
		'$location',
		'$window',
		'FileUpload',
		'SharedProperties',
		'Pictures',
		function($scope, $location, $window, FileUpload, SharedProperties,
				Pictures) {
			$scope.pictureTitle = "";
			$scope.headerText = "";
			$scope.uploadFile = function() {
				var uploadUrl = SharedProperties.getApiUrl() + "/content";
				var file = $scope.myFile;
				if (file) {
					FileUpload.uploadFileToUrl(file, uploadUrl).then(
							function(data) {
								var pictureObject = {
									name : $scope.pictureTitle,
									url : data.path
								};
								Pictures.save(pictureObject, function(data) {
									$scope.headerText = "File " + data
											+ " uploaded to server!";
									$location.path('/home');

								},
										function(error) {
											$scope.headerText = "Error"
													+ error.message;
										});
							});
				} else {
					$window.alert("Please select the file!")
				}
			};
		} ]);

funnyControllers.controller('CreateFunnyPictureController', [
		'$scope',
		'$window',
		'$modal',
		'$http',
		'SharedProperties',
		'Pictures',
		'Funnies',
		function($scope, $window, $modal, $http, SharedProperties, Pictures,
				Funnies) {
			$scope.template = {};
			$scope.funniesByTemplate = {};
			$scope.currentPage = 1;
			$scope.totalItems = 0;
			$scope.itemsPerPage = 6;

			Pictures.get({
				id : SharedProperties.getTemplateId()
			}, function(picture) {
				$scope.template = picture;
				$http(
						{
							url : SharedProperties.getApiUrl() + "/pictures/"
									+ $scope.template.id + "/funnies",
							method : "GET",
							params : {
								offset : ($scope.currentPage - 1)
										* $scope.itemsPerPage,
								limit : $scope.itemsPerPage
							}
						}).success(function(data, status) {
					$scope.funniesByTemplate = data.entities;
					$scope.totalItems = data.count;
				}).error(function(error) {
					console.log(error.statusText + " " + error.status);
				});
			});

			$scope.createFunnyPicture = function() {
				var postObject = new Object();
				postObject.name = $scope.template.name;
				postObject.header = $scope.headerText;
				postObject.footer = $scope.footerText;
				postObject.template = {};
				postObject.template.id = $scope.template.id;
				Funnies.save(postObject, function(data) {
					$scope.template = data;
				}, function(error) {
					console.log("Can't create picture" + error.statusText + " "
							+ error.status);
				});
			};

			$scope.pageChanged = function() {
				$http(
						{
							url : SharedProperties.getApiUrl() + "/pictures/"
									+ $scope.template.id + "/funnies",
							method : "GET",
							params : {
								offset : ($scope.currentPage - 1)
										* $scope.itemsPerPage,
								limit : $scope.itemsPerPage
							}
						}).success(function(data, status) {
					$scope.funniesByTemplate = data.entities;
					$scope.totalItems = data.count;
				}).error(function(error) {
					console.log(error.statusText + " " + error.status);
				});

			};

			$scope.modalOpen = function(funnyPicture) {
				$modal.open({
					templateUrl : 'html/modal/funnyPreviewModal.html',
					controller : 'FunnyPreviewModalController',
					resolve : {
						funnyPicture : function() {
							return funnyPicture;
						}
					}
				});
			};

			$scope.isButtonDisabled = function() {
				if ($scope.funnyPictureText.$valid) {
					return false;
				} else {
					return true;
				}
			}

			$scope.fullSize = function() {
				$window.open($scope.template.url);
			};

			$scope.showPagination = function() {
				return $scope.totalItems > $scope.itemsPerPage;
			};

		} ]);

funnyControllers.controller('ContactController', [
		'$scope',
		'Feedback',
		function($scope, Feedback) {
			$scope.alerts = [];
			$scope.feedback = {
				subject : "Select subject..."
			};
			$scope.sendFeedback = function() {
				Feedback.save($scope.feedback, function(data, status) {
					$scope.alerts.push({
						type : 'success',
						msg : 'Thank you ' + $scope.feedback.name
								+ ', your feedback was sent.'
					});
					console.log(data + " " + status);

				}, function(error) {
					console.log("Feedback can't sent " + error.statusText + " "
							+ error.status);
				});
			};
			$scope.isButtonDisabled = function() {
				if ($scope.contactForm.$valid) {
					return false;
				} else {
					return true;
				}
			}
		} ]);
