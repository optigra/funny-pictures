var mainModule = angular.module("mainModule", ['ngResource']);

mainModule.factory('Pictures', function($resource) {
  return $resource('http://localhost:8080/funny-pictures-rest-api/api/pictures/:id',{}, {'query': {method: 'GET', isArray: false}}); 
});


mainModule.filter('groupBy', function() {
	return function(items, groupedBy) {
		if (items) {
			var finalItems = [], thisGroup;
			for (var i = 0; i < items.length; i++) {
				if (!thisGroup) {
					thisGroup = [];
				}
				thisGroup.push(items[i]);
				if (((i + 1) % groupedBy) === 0) {
					finalItems.push(thisGroup);
					thisGroup = null;
				}
			}
			if (thisGroup) {
				finalItems.push(thisGroup);
			}
			return finalItems;
		}
	};
});


mainModule.directive('header', function() {
	return {
		restrict : 'E',
		templateUrl : "../html/directives/header.html",

	};
});

mainModule.directive('footer', function() {
	return {
		restrict : 'E',
		templateUrl : "../html/directives/footer.html",

	};
});

mainModule.controller('MainControler', [ '$scope', '$http' , 'Pictures' , function($scope, $http , Pictures) {
	$scope.imagePreview = "test.jpeg";
	var test = Pictures.query(function (){
		$scope.pictures =  test.entities; 
	});
	
} ]);


