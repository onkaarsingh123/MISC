var app = angular.module('libraryApp', ['ngRoute']);

app.config(['$routeProvider', function($routeProvider){
	$routeProvider
	.when('/home', {
		templateUrl:'views/home.html',
		controller:'homeController'
	})
	.otherwise({
		templateUrl:'views/default.html',
		controller:''
	});
}]);

app.controller('homeController', function($scope, $location){
	
	$scope.showView = function(data){
		if(data === 'new'){
			$scope.loginView = true;
			$scope.registerView = false;
			
		}
		else{
			$scope.loginView = false;
			$scope.registerView = true;		
		}
	}
	
});
