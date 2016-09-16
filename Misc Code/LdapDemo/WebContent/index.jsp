<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LDAP Authentication</title>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script>
var app = angular.module('myApp', []);
app.controller('formCtrl',['$scope','$http', function($scope,$http) {
    $scope.Login = function() {
    	
       alert( $scope.uName +".."+ $scope.password);
       
       $http.get('http://localhost:8080/LdapDemo/ldapCheck?name='+$scope.uName+'&password='+$scope.password).success(function(data, status, headers, config)
       {
    	   console.log("Success");
       }).
       error(function(data, status, headers, config) {
			console.log("Error !!!");
       });

    };
  
}]);
</script>


</head>
<body>
<br><br><br><br>
<div ng-app="myApp" ng-controller="formCtrl">
<form novalidate>
<table align="center">
	<tr>
		<td>User Name</td>
		<td><input type="text" ng-model="uName"></td>
	</tr>
	<tr>
		<td>Password</td>
		<td><input type="password" ng-model="password" ></td>
	</tr>	
	<tr>
		<td colspan="2" align="center"><button ng-click="Login()">LOGIN</button></td>
	</tr>
</table>
</form>
  
</div>

 </div>
</body>
</html>
