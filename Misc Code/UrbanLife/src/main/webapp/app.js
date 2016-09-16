'use strict';

// Declare app level module which depends on views, and components
//currently I have removed 'tokenValidationModule', and 'loginControllerModule' because we are getting error on Token controller

var app=angular.module('urbanLifeApp', ['ngRoute','ngUpload','ngMaterial','registrationControllerModule','loginControllerModule','businessControllerModule','ngMessages','ui.bootstrap','checklist-model']).
config(['$routeProvider','$mdThemingProvider', function($routeProvider,$mdThemingProvider) {
	  $routeProvider.otherwise({redirectTo: '/home'});
	  
	  $mdThemingProvider.theme('docs-dark', 'default')
      .primaryPalette('yellow')
      .dark();
	  
	}]);

app.service('merchantService', function () {
	  this.Merchant = {};
	  this.file=null;
	  
	});

app.service('businessService', function () {
	  this.Business = {};
	  this.User = {};
	  this.Other={};
	  this.file=null;
	  this.username=null;
	  
	});

app.service('cuisinesService', function () {
	  this.Cuisines = {};
	 // console.log('I am in service'+this.Cuisines)
	  
	  
	});

app.factory('tokenService', ['$http', function($http) {
	  
	return {
		checkUsertoken:function()
	               {
	 		       console.log("checkUsertoken");
	 		       
	 		        var credentials={};
	 				
	 		        credentials.username   =  "";
	 		        credentials.password   =  "";
	 		        credentials.prefix     =  "UL_CUSTOM_BO";
	 		        credentials.usertoken =  null;
	 		        credentials.flag   = "validateToken";
	 		        credentials.rememberme = "no";
	 		        var tokenUrl='/UrbanLife/service/api/v1/login/auth';
	 		       
	 		       if ('localStorage' in window && window['localStorage'] !== null) 
 	             	{
	 		    	  if (!(localStorage.getItem("usertoken") === null)) 
	  	             	{
	 		    		 credentials.usertoken=localStorage.getItem("usertoken");
	  	             	}
	 		    	 if (!(localStorage.getItem("username") === null)) 
	  	             	{
	 		    		 credentials.username=localStorage.getItem("username");
	  	             	}
	 		    	 if (!(localStorage.getItem("password") === null)) 
	  	             	{
	 		    		 credentials.password=localStorage.getItem("password");
	  	             	}
	 		    	 if (!(localStorage.getItem("rememberme") === null)) 
	  	             	{
	 		    		credentials.rememberme=localStorage.getItem("rememberme");
	 		    		
	  	             	}
 	             	}
	 		       else{
	 		    	   
	 		    	  console.log("local storage not supported");
	 		    	  
	 		       }
	 
	  	             
	 		       
	 		       console.log(" rememberme "+ localStorage.getItem("rememberme"));
	 		       
	 		      $http.post(tokenUrl, {'username':  credentials.username, 'password': credentials.password, 'prefix':credentials.prefix, 'usertoken':credentials.usertoken, 'flag':credentials.flag ,'rememberme':credentials.rememberme}
	 		        ).success(function(data, status, headers, config) {
	 		        	
					  	            	 		       console.log("usertoken  "+data.token);					  	            	 		        	 
					  	            	 		      	//console.log("username  "+businessService.username);
					  	            	 		        	   
					      	 
					  	            	 		           }).error(function(data, status) { 
					      	
					  	            	 		        	   		if(data.errorCode=="30020")
					  	            	 		        	   		{
					  	            	 		        	   			console.log("error data  "+data.errorCode+"status    usertoken is invalid"+status);
					  	            	 		        	   			window.location = "#/login";
					  	            	 		        	   		}
					  	            	 		           });
					  	
					  	                 }
	}
		  
	}]);


