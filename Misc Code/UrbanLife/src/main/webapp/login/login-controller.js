'use strict';

var app = angular.module('loginControllerModule', []).config(
		[ '$routeProvider', function($routeProvider) {
			
			$routeProvider.when('/login', {
				templateUrl : 'login/login.html',
				controller : 'loginController'
			}).when('/forgotPassword', {
		        templateUrl: 'login/forgot-password.html',
		        controller : 'forgotPasswordController'
		    }).when('/resetPassword', {
			    templateUrl: 'login/reset-password.html',
			    controller : 'resetPasswordController'
			}).when('/enterPassword', {
			    templateUrl: 'login/enter-password.html',
				controller : 'resetPasswordController'
			});
		 
		} ]);

app.controller('loginController', ['$scope','$http',  function($scope,$http){

	$scope.doLogin = function(){
		
		console.log("HEre in login");
		
		 var credentials={};
	     if($scope.login.username!=null && $scope.login.password!=null)
	     	{
			
			console.log("remember me"+$scope.login.remember);
			
	        credentials.username   =  $scope.login.username.trim();
	        credentials.password   =  $scope.login.password.trim();
	        credentials.prefix    =  "UL_CUSTOM_BO";
	        credentials.usertoken =  null;
	        credentials.flag   = "generateToken"; 
	        credentials.rememberme = "no";
	        
	        
	        if($scope.login.remember)
			 {
	        	credentials.rememberme = "yes";
			 }
	        
	        var loginUrl='/UrbanLife/service/api/v1/login/auth';
	        
	        
	        if ('localStorage' in window && window['localStorage'] !== null) 
			{
	        	if (!(localStorage.getItem("usertoken") === null))
	        	{
	        		credentials.usertoken = localStorage.getItem("usertoken");
	        		
	        	}
			}
	        
	        
	        
	        if($scope.login.remember)
			 {
	        	if ('localStorage' in window && window['localStorage'] !== null) 
					{
	        			localStorage.setItem("username",credentials.username);
	        			localStorage.setItem("password",credentials.password);
	        			localStorage.setItem("rememberme",credentials.rememberme);
					}
			 }
	        else
	        	{
	        	if ('localStorage' in window && window['localStorage'] !== null) 
				{
	    			localStorage.setItem("username","");
	    			localStorage.setItem("password","");
	    			localStorage.setItem("rememberme","no");
				}
	        	}
	        
	    
	        
	        $http.post(loginUrl, {'username':  credentials.username, 'password': credentials.password, 'prefix':credentials.prefix, 'usertoken':credentials.usertoken, 'flag':credentials.flag, 'rememberme':credentials.rememberme  }
	        ).success(function(data, status, headers, config) {
	        
	        	console.log("suceess data  "+data);
		    		if(data=="30020")
		    	 		{
		    			$scope.login.servererror=data;
		    	 		}
		    	 	else if ('localStorage' in window && window['localStorage'] !== null) 
		    	 					{
		    	 		
		    	 							//Set the token if it is null
		    	 		                   if (localStorage.getItem("usertoken") === null) {
												localStorage.setItem("usertoken",data.token);
												console.log("usertoken  is set");
		    	 		    	 			
											}
		    	 		    	 		else{
		    	 		    	 			
		    	 		    	 			//Set the new token if it is already existing
		    	 		    	 			localStorage.removeItem("usertoken");
		    	 		    	 			localStorage.setItem("usertoken",data.token);
		    	 		 					console.log("new user usertoken is set  "+localStorage.getItem("usertoken"));
		    	 		    	 			}
		    	 						
		    	 					} 
		    	 					else {
		    	 						console.log('Your browser do not support local storage');
		    	 					}
		    		
		    		
		    		window.location = "#/welcomebusiness";

		     }).error(function(data, status) { 
		    	 console.log("error data  "+data.errorCode+"status    "+status);
		    	 $scope.login.servererror=data.errorCode;
		    	 	
		     });
	       
	     	}
	     };
		
	}]); 



app.controller('forgotPasswordController', ['$scope','$http',  function($scope,$http){
	
	$scope.validateEmail = function(){
		
		var emailAddress=$scope.fppassword.email.trim();
		console.log("forgotPasswordController "+emailAddress);
		
		  var validateEmailUrl='/UrbanLife/service/api/v1/login/forgotpassword';
		
		  $http.post(validateEmailUrl, {'emailId': emailAddress}
	        ).success(function(data, status, headers, config) {
	        	
	        	console.log("Email is sent");
	        	$scope.fppassword.mailsent=true;
	        	 $scope.fppassword.servererror=false;
	        	
	        }).error(function(data, status) { 
	        	
	        	$scope.fppassword.mailsent=false;
	        	
		    	 
	        	 console.log("error data  "+data.errorCode+"status    "+status);
		    	 $scope.fppassword.servererror=data.errorCode;
		    	 	
		     });
		
	 };
		
}]); 

app.controller('resetPasswordController', ['$scope','$http',"$location","loginService",  function($scope,$http,$location,loginService){

	console.log("$location.search()     "+$location.search());
	
	 var prefix    =  "UL_CUSTOM_BO";
	var params=$location.search();
	
	 var str = params.param1;
	 var res1 = str.split("@");

   	var str = res1[0].replace("_", " ");
   	 
 
 	 
  	//$scope.username=res;
  	
  	var index=str.indexOf(" ");

  	var res = str.charAt(0).toUpperCase()+str.substring(1);

  	var res2=res.substring(0,index+1)+res.charAt(index+1).toUpperCase()+res.substring(index+2);
  	
  	//$scope.username=res1;
	   
	
	$scope.userId= res2;
	
	console.log("params.param1     "+params.param1);
	
	$scope.resetPassword = function(){
		
		console.log("resetPassword function");
		var pass  = $scope.fpreset.password.trim();
		var rPass = $scope.fpreset.password_verify.trim();
		 //var resetPassUrl='http://localhost:8080/UrbanLife/service/api/v1/login/changePassword';
		 
		 var resetPassUrl='/UrbanLife/service/api/v1/login/changepassword';
		 
		console.log("resetPassword function     "+pass+""+rPass);
		
		$http.post(resetPassUrl, {'emailId':  params.param1, 'password': rPass}
        ).success(function(data, status, headers, config) {
        	
        	console.log("Password reset succesful");
        	loginService.doLogin(params.param1, pass, prefix);
        	
        	
        }).error(function(data, status) { 
	    	 
        	 console.log("error data  "+data.errorCode+"status    "+status);
	    	 $scope.fpreset.servererror=data.errorCode;
	    	 	
	     });
		
		
	};
	
		
}]);






app.factory('loginService', ['$http', function($http) {
	  
	return {
		doLogin:function(username, password, prefix)
	               {
			
			console.log("calling loginService ");
			
			
			 var credentials={};
		     if(username!=null && password!=null)
		     	{
				
				//console.log("remember me"+$scope.login.remember);
				
		        //credentials.username   =  $scope.login.username.trim();
		       //credentials.password   =  $scope.login.password.trim();
		        //credentials.prefix    =  "UL_CUSTOM_BO";
				credentials.username   =  username;
			    credentials.password   =  password;
			    credentials.prefix    =  prefix;
		        credentials.usertoken =  null;
		        credentials.flag   = "generateToken"; 
		        credentials.rememberme = "no";
		        
		        
		        var loginUrl='/UrbanLife/service/api/v1/login/auth';
		        
		        
		       if ('localStorage' in window && window['localStorage'] !== null) 
				{
		        	if (!(localStorage.getItem("usertoken") === null))
		        	{
		        		credentials.usertoken = localStorage.getItem("usertoken");
		        		
		        	}
		        	
		        	if (!(localStorage.getItem("rememberme") === null))
		        	{
		        		 credentials.rememberme = localStorage.getItem("rememberme");
		        		
		        	}
				}
		        
		        
		        
		       if(credentials.rememberme==="yes")
				 {
		        	if ('localStorage' in window && window['localStorage'] !== null) 
						{
		        			localStorage.setItem("username",credentials.username);
		        			localStorage.setItem("password",credentials.password);
		        			localStorage.setItem("rememberme",credentials.rememberme);
						}
				 }
		        else
		        	{
		        	if ('localStorage' in window && window['localStorage'] !== null) 
					{
		    			localStorage.setItem("username","");
		    			localStorage.setItem("password","");
		    			localStorage.setItem("rememberme","no");
					}
		        	}
		         
		    
		        
		        $http.post(loginUrl, {'username':  credentials.username, 'password': credentials.password, 'prefix':credentials.prefix, 'usertoken':credentials.usertoken, 'flag':credentials.flag, 'rememberme':credentials.rememberme  }
		        ).success(function(data, status, headers, config) {
		        
		        	console.log("suceess data  "+data);
			    		if(data=="30020")
			    	 		{
			    			console.log("Error while log in again")
			    	 		}
			    	 	else if ('localStorage' in window && window['localStorage'] !== null) 
			    	 					{
			    	 		
			    	 							//Set the token if it is null
			    	 		                   if (localStorage.getItem("usertoken") === null) {
													localStorage.setItem("usertoken",data.token);
													console.log("usertoken  is set");
			    	 		    	 			
												}
			    	 		    	 		else{
			    	 		    	 			
			    	 		    	 			//Set the new token if it is already existing
			    	 		    	 			localStorage.removeItem("usertoken");
			    	 		    	 			localStorage.setItem("usertoken",data.token);
			    	 		 					console.log("new user usertoken is set  "+localStorage.getItem("usertoken"));
			    	 		    	 			}
			    	 						
			    	 					} 
			    	 					else {
			    	 						console.log('Your browser do not support local storage');
			    	 					}
			    		
			    		
			    		window.location = "#/welcomebusiness";

			     }).error(function(data, status) { 
			    	 console.log("error data  "+data.errorCode+"status    "+status);
			    	
			    	 	
			     });
		       
		     	}
	 		       
	               }
	}
	  
	}]);