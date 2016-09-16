var healthApp = angular.module('healthApp', ['ngRoute']);

healthApp.config(['$routeProvider', function($routeProvider){
	$routeProvider
	.when('/', {
		templateUrl:'views/home.html',
		controller:'homeController'
	})
	.when('/patient', {
		templateUrl:'views/patient.html',
		controller:'getPatientDetails'
	})
	.otherwise({
		templateUrl:'views/home.html',
		controller:'homeController'
	});
}]);

healthApp.service("PId", function () {
	var patentId = "";
	return {
	    getPatientId: function () {
	        return patentId;
	    },
	    setPatientId: function (value) {
	    	patentId = value;
	    }
	};	});

healthApp.controller('getPatientDetails', function($scope, $http, PId){
	
	patientId = PId.getPatientId();
	$http.get("http://pslfhir.persistent.co.in:8080/baseDstu2/Patient/"+ patientId)
	.then(function(response) {
        $scope.patientData = response.data;
        console.log("pID " + patientId);
        $scope.pName = $scope.patientData.name[0].family[0] + " " +$scope.patientData.name[0].given[0];
        $scope.dob = $scope.patientData.birthDate;
        $scope.address = $scope.patientData.address;
    });
	
	$scope.getCarePLan = function(){
		$scope.cp = true;
		$scope.mo=false;
		$scope.dOrder=false;
		$scope.ai=false;
		
		$http.get("http://pslfhir.persistent.co.in:8080/baseDstu2/CarePlan?patient="+patientId)
		.then(function(response) {
	        $scope.cpData = response.data.entry;
	        
		});
		
		
	};
	
	$scope.getAI = function(){
		$scope.ai = true;
		$scope.cp = false;
		$scope.mo=false;
		$scope.dOrder=false;
		
		$http.get("http://pslfhir.persistent.co.in:8080/baseDstu2/AllergyIntolerance?patient="+patientId)
		.then(function(response) {
	        $scope.aiData = response.data.entry;
	        
		});
	};

	$scope.getDO = function(){
		$scope.dOrder = true;
		$scope.cp = false;
		$scope.mo=false;
		$scope.ai=false;
		
		$http.get("http://pslfhir.persistent.co.in:8080/baseDstu2/DiagnosticOrder?patient="+patientId)
		.then(function(response) {
	        $scope.doData = response.data.entry;
	        
		});
	};
	
	$scope.getMO = function(){
		$scope.mo = true;
		$scope.cp = false;
		$scope.dOrder=false;
		$scope.ai=false;
		
		$http.get("http://pslfhir.persistent.co.in:8080/baseDstu2/MedicationOrder?patient="+patientId)
		.then(function(response) {
	        $scope.moData = response.data.entry;
	        
		});
	};

	
	
});

healthApp.controller('homeController', function($scope, $location, PId){
	
	$scope.getPatientData = function(){
		PId.setPatientId($scope.patientId);
		$location.url("/patient");
	};
	
});