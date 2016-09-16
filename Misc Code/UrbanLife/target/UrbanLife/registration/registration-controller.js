'use strict';

var app = angular.module('registrationControllerModule', []).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'registration/home.html',
				controller : 'homeCtrl'
			}).when('/signup', {
				templateUrl : 'registration/registration-signup.html',
				controller : 'registrationCtrl'
			}).when('/payment', {
		        templateUrl: 'registration/registration-payment.html',
		        controller : 'paymentCtrl'
		      }).when('/success', {
			        templateUrl: 'registration/registration-success.html'
			        //controller : 'paymentCtrl'
			      }).otherwise({
				redirectTo : '/home'
			});
		} ]);


/*app.directive("passwordVerify", function() {
   return {
      require: "ngModel",
      scope: {
        passwordVerify: '='
      },
      link: function(scope, element, attrs, ctrl) {
        scope.$watch(function() {
            var combined;

            if (scope.passwordVerify || ctrl.$viewValue) {
               combined = scope.passwordVerify + '_' + ctrl.$viewValue; 
            }                    
            return combined;
        }, function(value) {
            if (value) {
                ctrl.$parsers.unshift(function(viewValue) {
                    var origin = scope.passwordVerify;
                    if (origin !== viewValue) {
                        ctrl.$setValidity("passwordVerify", false);
                        return undefined;
                    } else {
                        ctrl.$setValidity("passwordVerify", true);
                        return viewValue;
                    }
                });
            }
        });
     }
   };
});
*/




app.service('registerService', ['$http', function ($http) {
	
    this.createMerchant = function(file,merchant,cardNo,ccvNumber,month,year,cctype,uploadUrl,ccname){
    	
    	var merchantstr=angular.toJson(merchant, false);
        var fd = new FormData();
        fd.append('photo', file);
        fd.append('merchant',merchantstr);
        fd.append('cardNumber',cardNo);
        fd.append('ccv_no',ccvNumber);
        fd.append('exp_month',month);
        fd.append('exp_year',year);
        fd.append('cctype',cctype);
        fd.append('ccname',ccname);
      
      //  $timeout(function(){
        	console.log("inside timeout");
        	
        	$http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
           
        })
        .success(function(data){
            console.log("sucess"+data);
            window.location ="#/success";
          //  return data;
           })
        .error(function(data){
        	console.log("failure"+data);
        	 return data;
        });
        	
   // }, 2000);

    }
}]);


app.controller('homeCtrl', ['$scope','tokenService', function($scope,tokenService){
    
	/*$scope.checkUsertokensignup=function()
	 {
		
			tokenService.checkUsertoken();
	
	 };*/

}]); 


app.controller('registrationCtrl', ['$scope', 'registerService', 'merchantService','tokenService', function($scope, registerService, merchantService,tokenService){
    
	$scope.checkUsertokensignup=function()
	 {
		
			tokenService.checkUsertoken();
	
	 };
	 
	 var merchant = merchantService.Merchant;
	 //alert(JSON.stringify(merchant));
	 if(merchant.length!=0){
		 if( merchant.firstName!=undefined){
		//alert("hi="+ merchant.firstname);
		$scope.user=merchant;
//		$scope.user.firstname= merchant.firstname;
//		$scope.user.middleName= merchant.middleName;
//	     $scope.user.lastName = merchant.lastName;
//	       $scope.user.eemail= merchant.email ;
//	        $scope.user.telephone=merchant.telephone;
//	        $scope.user.password=merchant.password;
//	        $scope.user.password_verify=merchant.rpassword;
	       $scope.photo= merchantService.file;
		 }
	 }     
	
	$scope.nextPage = function(){
    	
        var merchant={};
        merchant.firstName=$scope.user.firstname.trim();
        merchant.middleName=$scope.user.middleName;
        merchant.lastName=$scope.user.lastName;
        merchant.emailId=$scope.user.eemail;
        merchant.contactNumber=$scope.user.telephone.trim();
        merchant.password=$scope.user.password;
        merchant.rpassword=$scope.user.password_verify;
        
        merchantService.Merchant=merchant;
        
        merchantService.file=$scope.photo;
        
      //  console.log($scope.photo);
        //$scope.filename= photo.name;
        
        window.location = "#/payment";
        //var uploadUrl='http://localhost:8080/RESTfulExample/service/api/v1/merchants/add';
        //registerService.createMerchant(file, merchant, uploadUrl);
       
    };
    
}]); 

app.controller('paymentCtrl', ['$scope', 'registerService','merchantService','$http','tokenService','$modal', function($scope, registerService, merchantService,$http,tokenService,$modal){
	 	
	
	$scope.checkUsertokenpayment=function()
	 {
		
			tokenService.checkUsertoken();
	
	 };
	
	 
	  $scope.countryList = [
	        		    { id: 'USA',  value: 'USA'},
	        		    { id: 'GBR',  value: 'UK'},
	        		    { id: 'SGP',  value: 'Singapore'},
	        		  ];
	  
	  $scope.onCountryChange = function(){
		  var Url='/UrbanLife/service/api/v1/city/'+$scope.ccinfo.country.id;
	  		
    	    $http.get(Url).
  	        success(function(data) {
  	        	console.log("success "+data);
  	        	 $scope.stateList=data;
  	        	
  	        	
  	            
  	        }).error(function(data) {
  	        	console.log("error "+data);
  	        	
  	        });
    	    
	  };
	  
	  
 $scope.onStateChange = function(){
	 var Url='/UrbanLife/service/api/v1/city/'+$scope.ccinfo.country.id+'/'+$scope.ccinfo.state;
	 
	    $http.get(Url).
       success(function(data) {
       	console.log("success "+data);
       	$scope.ccinfo.city="";
       	$scope.cityList=data;
    	
       
       }).error(function(data) {
       	console.log("error "+data);
       });
	    
	  };
	  

	
	$scope.registerMerchant = function(){ 
	    var merchant = merchantService.Merchant;
		var file     = merchantService.file;
		var ccname= $scope.ccinfo.name;
		var cardNo= $scope.ccinfo.ccnumber;
		var ccvNumber= $scope.ccinfo.securityCode;
		var month=  $scope.ccinfo.expiry_month;
		var year= $scope.ccinfo.expiry_year;
		var cctype= $scope.ccinfo.ccType;	
		
		
		
		merchant.state=$scope.ccinfo.state;
		merchant.city=$scope.ccinfo.city.name;
		
		console.log($scope.ccinfo.billingAdd1 +'=========='+$scope.ccinfo.billingAdd2);
		 merchant.addressLine1=$scope.ccinfo.billingAdd1;
		 merchant.addressLine2=$scope.ccinfo.billingAdd2;
		// merchant.billingAddress2=$scope.ccinfo.billingAdd2;
		
		merchant.country=$scope.ccinfo.country.value;
		merchant.zipCode=$scope.ccinfo.postCode;
	//	merchant.Address=$scope.ccinfo.billingAdd;
		
		console.log(merchant);	
		 console.log("----------");
		 console.log(merchantService.file);
		 
		 //var testUrl='http://localhost:8080/UrbanLife/service/api/v1/merchants/emailValidation/randomMail.@gmail.com';
		var uploadUrl='/UrbanLife/service/api/v1/merchants/add';

		 console.log("outside if");
		 
		// var data=registerService.createMerchant(file, merchant,cardNo,ccvNumber,month,year,cctype, TestUrl,ccname);

	    	var merchantstr=angular.toJson(merchant, false);
	        var fd = new FormData();
	        fd.append('photo', file);
	        fd.append('merchant',merchantstr);
	        fd.append('cardNumber',cardNo);
	        fd.append('ccv_no',ccvNumber);
	        fd.append('exp_month',month);
	        fd.append('exp_year',year);
	        fd.append('cctype',cctype);
	        fd.append('ccname',ccname);
	      
	      //  $timeout(function(){
	        	console.log("inside timeout");
	        	
	        	$http.post(uploadUrl, fd, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	           
	        })
	        .success(function(data){
	            console.log("sucess"+data);
	            //window.location = "#/welcomebusiness";
	            if(data!="registartion successful")
	         	{
	    			 console.log("in if"+angular.toJson(data, true));
	    			
	    			 //code to show popup message
	                 var modalInstance = $modal.open({
	                     templateUrl: 'popup.html',
	                     controller: 'popupCtrl',
	                     size: 5,
	                     resolve: {
	                       items: function () {
	                                                  
	                       }
	                     }
	                   });
	                      
	               
	                   modalInstance.result.then(function () {
	                   
	                      console.log('we are in result');
	               
	                   }, function () {
	                	   console.log('Modal not displayed');
	                    // $log.info('Modal dismissed at: ' + new Date());
	                   });

	    			 
	    			 
	    			 
	         	$scope.ccinfo.servererror=data;
	         	 console.log("$scope.ccinfo.servererror"+$scope.ccinfo.servererror);
	         	}
	         //   return data;
	           })
	        .error(function(data){
	        	
	        	console.log("failure"+data.errorCode);
	        	//console.log("failure"+angular.toJson(data, true));
	        	$scope.ccinfo.servererror=data.errorCode;
	        	
	        	
	        	 //code to show popup message
                var modalInstance = $modal.open({
                    templateUrl: 'errorpopup.html',
                    controller: 'errorpopupCtrl',
                    size: 5,
                    resolve: {
                      items: function () {
                                                 
                      }
                    }
                  });
                     
              
                  modalInstance.result.then(function () {
                  
                     console.log('we are in result');
              
                  }, function () {
                	  console.log('Modal not displayed');
                   // $log.info('Modal dismissed at: ' + new Date());
                  });

	   
	        });
		
		
		
		
		
	 };
	 
	 $scope.back = function(){ 
		 var merchant = merchantService.Merchant;
	      // alert(JSON.stringify(merchant));
	       
	       if(merchant.length!=0){
	              $scope.user=merchant;
	              $scope.photo= merchantService.file;
	                }
	 window.location = "#/signup";
		
	 }
}]); 


//added pop up controller

app.controller('popupCtrl', function ($scope, $modalInstance ) {

    
    $scope.ok = function () {
         
      $modalInstance.close();
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  });

//added pop up errorpopupCtrlcontroller

app.controller('errorpopupCtrl', function ($scope, $modalInstance ) {

    
    $scope.ok = function () {
         
      $modalInstance.close();
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  });





app.controller
( 'paymentCtrl2'
, function($scope,$locale) {
    $scope.currentYear = new Date().getFullYear()
    $scope.currentMonth = new Date().getMonth() + 1
    $scope.months = $locale.DATETIME_FORMATS.MONTH
    $scope.ccinfo = {type:undefined}
    $scope.save = function(data){
      if ($scope.paymentForm.$valid){
        console.log(data) // valid data saving stuff here
      }
    }
    
  }
)



app.directive
( 'creditCardType'
, function(){
    var directive =
      { require: 'ngModel'
      , link: function(scope, elm, attrs, ctrl){
          ctrl.$parsers.unshift(function(value){
        	  
        	 
        	  
            scope.ccinfo.type =
              (/^5[1-5]/.test(value)) ? "mastercard"
              : (/^4/.test(value)) ? "visa"
              : (/^3[47]/.test(value)) ? 'amex'
              : (/^6011|65|64[4-9]|622(1(2[6-9]|[3-9]\d)|[2-8]\d{2}|9([01]\d|2[0-5]))/.test(value)) ? 'discover'
              : undefined
            ctrl.$setValidity('invalid',!!scope.ccinfo.type)
            
            if(scope.ccinfo.type=="visa")
            	{
            	scope.ccinfo.icon1=true;
            	scope.ccinfo.icon2=false;
            	scope.ccinfo.icon3=false;
            	}
            else if(scope.ccinfo.type=="mastercard")
        	{
        	scope.ccinfo.icon1=false;
        	scope.ccinfo.icon2=true;
        	scope.ccinfo.icon3=false;
        	}
            else if(scope.ccinfo.type=="amex")
        	{
        	scope.ccinfo.icon1=false;
        	scope.ccinfo.icon2=false;
        	scope.ccinfo.icon3=true;
        	}
            
            
            return value
          })
        }
      }
    return directive
    }
  )

app.directive
( 'cardExpiration'
, function(){
    var directive =
      { require: 'ngModel'
      , link: function(scope, elm, attrs, ctrl){
          scope.$watch('[ccinfo.month,ccinfo.year]',function(value){
            ctrl.$setValidity('invalid',true)
            if ( scope.ccinfo.year == scope.currentYear
                 && scope.ccinfo.month <= scope.currentMonth
               ) {
              ctrl.$setValidity('invalid',false)
            }
            return value
          },true)
        }
      }
    return directive
    }
  )

app.filter
( 'range'
, function() {
    var filter = 
      function(arr, lower, upper) {
        for (var i = lower; i <= upper; i++) arr.push(i)
        return arr
      }
    return filter
  }
)




app.directive("inuse", function($q, $timeout,$http) {
 
    return {
      restrict: "A",
      require: "ngModel",
      link: function(scope, element, attributes, ngModel) {
    	  
    	
        ngModel.$asyncValidators.inuse = function(modelValue) {
          
    // var emailReg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
          //console.log(modelValue+"modelValue");
        // if(emailReg.test(modelValue))
       //{
      	  var defer = $q.defer();
      		$timeout(function(){
      			
      			
      			
      			var Url='/UrbanLife/service/api/v1/login/emailValidation/'+modelValue;
    	  		
          	    $http.get(Url).
        	        success(function(data) {
        	        	console.log("success "+data);
        	        	
        	        	if(data=="2001")
        	        		{
        	        		console.log("if ");
        	        		
        	        		defer.reject();
        	        		}
        	        	else{
        	        		console.log("else ");
        	        		defer.resolve();
        	        	}
        	        	
        	            
        	        }).error(function() {
        	        	console.log("error ");
        	        	defer.resolve();
        	        });
          }, 2000);
          return defer.promise;
        
          
 //		}
       
          
          
        }
      }
   };
});


 

