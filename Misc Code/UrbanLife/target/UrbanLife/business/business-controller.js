(function(){
	
	
	'use strict';


var app = angular.module('businessControllerModule', []).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/welcomebusiness', {
				templateUrl : 'business/business-welcome.html',
				controller : 'welcomeBusinessCtrl'
			}).when('/addbusiness', {
				templateUrl : 'business/business-add.html',
				controller : 'addBusinessCtrl'
			}).when('/details', {
		        templateUrl: 'business/business-details.html',
		        controller : 'detailsBusinessCtrl'
		    }).when('/welcomeoffer', {
			        templateUrl: 'offer/welcome-offer.html',
			        controller : 'welcomeOfferCtrl'
			      })
		 
		} ]);



app.controller('welcomeBusinessCtrl', ['$scope','businessService','tokenService','$http',  function($scope,businessService,tokenService,$http){

	$scope.checkUsertokenwelocme=function()
	 {
		tokenService.checkUsertoken();
	
	 };
	 
	  $scope.getsessiondata = function(){
		  
			
			console.log("getsessiondata");
		  
		  var Url='/UrbanLife/service/api/v1/login/getsessiondata';
	  		
    	    $http.get(Url).
  	        success(function(data) {
  	        	console.log("success "+JSON.stringify(data));
  	        	
  	        	var str = data.replace("_", " ");
  	        	 
  	        	var index=str.indexOf(" ");

  	        	var res = str.charAt(0).toUpperCase()+str.substring(1);

  	        	var res1=res.substring(0,index+1)+res.charAt(index+1).toUpperCase()+res.substring(index+2);

  	        	
  	        	
  	        	$scope.username=res1;
  	        	
  	            
  	        }).error(function(data) {
  	        	console.log("error "+data);
  	        	
  	        });
    	    
	  };
	
	
	
	$scope.goToBusiness = function(){
			window.location = "#/addbusiness";
	  };

}]);

app.controller('welcomeOfferCtrl', ['$scope','businessService','tokenService','$http',  function($scope,businessService,tokenService,$http){

	$scope.checkUsertokenwelocmeOffer=function()
	 {		
		tokenService.checkUsertoken();
		
	 };
	 
	 $scope.getsessiondata = function(){
		  
			
			console.log("getsessiondata");
		  
		  var Url='/UrbanLife/service/api/v1/login/getsessiondata';
	  		
 	    $http.get(Url).
	        success(function(data) {
	        	console.log("success "+JSON.stringify(data));
	        	 

  	        	var str = data.replace("_", " ");
  	        	 
  	        	//$scope.username=res;
  	        	
  	        	var index=str.indexOf(" ");

  	        	var res = str.charAt(0).toUpperCase()+str.substring(1);

  	        	var res1=res.substring(0,index+1)+res.charAt(index+1).toUpperCase()+res.substring(index+2);
  	        	
  	        	$scope.username=res1;
  	        	
  	        	
	            
	        }).error(function(data) {
	        	console.log("error "+data);
	        	
	        });
 	    
	  };
	
	
	
	

}]);





app.controller('addBusinessCtrl', ['$scope','$http','businessService','tokenService',  function($scope,$http,businessService,tokenService){
	
	$scope.checkUsertokenaddBusiness=function()
	 {
		
			tokenService.checkUsertoken();
	
	 };
	 
	 
	 $scope.countryList = [
		        		    { id: 'USA',  value: 'USA'},
		        		    { id: 'GBR',  value: 'UK'},
		        		    { id: 'SGP',  value: 'Singapore'},
		        		  ];
		  
		  $scope.onCountryChange = function(){
			  var Url='/UrbanLife/service/api/v1/city/'+$scope.business.country.id;
		  		
	    	    $http.get(Url).
	  	        success(function(data) {
	  	        	console.log("success "+data);
	  	        	
	  	        	$scope.business.state="";
	  	        	 $scope.stateList=data;
	  	            
	  	        }).error(function(data) {
	  	        	console.log("error "+data);
	  	        	
	  	        });
	    	    
		  };
		  
		  
	 $scope.onStateChange = function(){
		 var Url='/UrbanLife/service/api/v1/city/'+$scope.business.country.id+'/'+$scope.business.state;
		 
		    $http.get(Url).
	       success(function(data) {
	       	console.log("success "+data);
	       	$scope.business.city="";
	       	$scope.cityList=data;
	    	
	       
	       }).error(function(data) {
	       	console.log("error "+data);
	       });
		    
		  };
	 
	 
	 var business = businessService.Business;	
     var user = businessService.User;
	 $scope.addBusiness = function(){
		 
		 console.log($scope.business.city.name+ ""+$scope.business.country.value+""+$scope.business.state);
    	
        var business={};
        business.name    		    =             $scope.business.name.trim();
        business.contactNumber  		    =             $scope.business.phone;
        business.emailId  		    =             $scope.business.email;
        business.address		    =             $scope.business.address;
        business.city   		    =             $scope.business.city.name;
        business.country 			=		      $scope.business.country.value;
	    business.state   			=			  $scope.business.state;
	    business.tripAdvisorId 		= 		      $scope.business.tripadvisorId;
	 
        businessService.Business=business;
        
	    businessService.photo=$scope.photo;
	    
	   //adding manager's data.	    
        if($scope.business.manager==true){
            
            user.firstName = $scope.business.mngfirstname;
            user.middleName = $scope.business.mngmiddleName;
            user.lastName = $scope.business.mngmiddleName;
            user.emailId = $scope.business.mnguseremail;
         }

	    
	    
        
       console.log(businessService.Business);
      // console.log($scope.photo);
      // console.log(businessService.file)
        //$scope.filename= photo.name;
    
        window.location = "#/details";
     
       
    };
	

    
}]); 



/*Adding Business details controller for details page*/

app.controller('detailsBusinessCtrl', ['$scope','$http','businessService', '$timeout','cuisinesService','tokenService', function($scope,$http,businessService, $timeout, cuisinesService,tokenService){

	$scope.checkUsertokenBusinessdetails=function()
	 {
		
			tokenService.checkUsertoken();
	
	 };
	 
	$scope.loadTime = function() {
		$scope.times = [];
		return $timeout(function() {
		  $scope.times = [
		    { id: 1,  value: '01:00'},
		    { id: 2,  value: '01:30'},
		    { id: 3,  value: '02:00'},
		    { id: 4,  value: '02:30'},
		    { id: 5,  value: '03:00'},
		    { id: 6,  value: '03:30'},
		    { id: 7,  value: '04:00'},
		    { id: 8,  value: '04:30'},
		    { id: 9,  value: '05:00'},
		    { id: 10, value: '05:30'},
		    { id: 11, value: '06:00'},
		    { id: 12, value: '06:30'},
		    { id: 13, value: '07:00'},
		    { id: 14, value: '07:30'},
		    { id: 15, value: '08:00'},
		    { id: 16, value: '08:30'},
		    { id: 17, value: '09:00'},
		    { id: 18, value: '09:30'},
		    { id: 19, value: '10:00'},
		    { id: 20, value: '10:30'},
		    { id: 21, value: '11:00'},
		    { id: 22, value: '11:30'},
		    { id: 23, value: '12:00'},
		    { id: 34, value: '12:30'},
		  ];
		
		}, 650);

	};
	
	 
	 
	/*Save Business will save al the Business from add Business page and details Business */
	$scope.saveBusiness= function(){
		
		
		var business = businessService.Business;
	//	console.log(cuisinesService.Cuisines);
		var cuisines = cuisinesService.Cuisines;
		var user = businessService.User;
		
		
		business.businessDetails    =             $scope.business.businessDetails;
		business.minSeats   		=             $scope.business.minSeats;
		business.maxSeats   		=             $scope.business.maxSeats;
		
		//Time Zone for week
		business.monStrTz           =             $scope.business.monStrTz;
		business.monEndTz           =             $scope.business.monEndTz;
		business.tueStrTz           =             $scope.business.tueStrTz;
		business.tueEndTz           =             $scope.business.tueEndTz;
		business.wedStrTz           =             $scope.business.wedStrTz;
		business.wedEndTz           =             $scope.business.wedEndTz;
		business.thuStrTz           =             $scope.business.thuStrTz;
		business.thuEndTz           =             $scope.business.thuEndTz;
		business.friStrTz           =             $scope.business.friStrTz;
		business.friEndTz           =             $scope.business.friEndTz;
		business.satStrTz           =             $scope.business.satStrTz;
		business.satEndTz           =             $scope.business.satEndTz;
		business.sunStrTz           =             $scope.business.sunStrTz;
		business.sunEndTz           =             $scope.business.sunEndTz;
		
		
		// Check Boxes for week
		business.moncb				=             $scope.business.moncb;
		business.tuecb				=             $scope.business.tuecb;
		business.wedcb				=             $scope.business.wedcb;
		business.thucb				=             $scope.business.thucb;
		business.fricb				=             $scope.business.fricb;
		business.satcb				=             $scope.business.satcb;
		business.suncb				=             $scope.business.suncb;
		
		
		
	
		//adding cuisines this is used from a directive
	//	Business.cuisines 			=             cuisines;
		
		business.bookingGraceTime        =             $scope.business.gracePeriod;
		business.bookingAgreedTime       =             $scope.business.expiryPeriod
		
				
		business.hours=""
			
		//for mon
		
		if((angular.isUndefined(business.moncb) || business.moncb==false) && (!(angular.isUndefined($scope.business.monStrtime)) || !(angular.isUndefined($scope.business.monEndtime))) ){	
		//if(angular.isUndefined(Business.moncb) || Business.moncb==false ){
			business.monStrtime         =  			  $scope.business.monStrtime.value;
			business.monEndtime         =             $scope.business.monEndtime.value;
			business.hours += 'mon- '+business.monStrtime+' '+business.monStrTz+', '+business.monEndtime+' '+business.monEndTz+' #';
		
		}else{
			
			business.hours += 'mon- 00:00 AM, 00:00 PM #';
		}
		
		//for tue
		if((angular.isUndefined(business.tuecb) || business.tuecb==false) && (!(angular.isUndefined($scope.business.tueStrtime)) || !(angular.isUndefined($scope.business.tueEndtime))) ){
		//if(angular.isUndefined(Business.tuecb) || business.tuecb==false ){
			business.tueStrtime         =  			  $scope.business.tueStrtime.value;
			business.tueEndtime         =             $scope.business.tueEndtime.value;
			business.hours += 'tue- '+business.tueStrtime+' '+business.tueStrTz+', '+business.tueEndtime+' '+business.tueEndTz+' #';
		
		}else{
			
			business.hours += 'tue- 00:00 AM, 00:00 PM #';
		}
		
		//for wed
		if((angular.isUndefined(business.wedcb) || business.wedcb==false) && (!(angular.isUndefined($scope.business.wedStrtime)) || !(angular.isUndefined($scope.business.wedEndtime))) ){
		//if(angular.isUndefined(Business.wedcb) || Business.wedcb==false ){
			business.wedStrtime         =  			  $scope.business.wedStrtime.value;
			business.wedEndtime         =             $scope.business.wedEndtime.value;
			business.hours += 'wed- '+business.wedStrtime+' '+business.wedStrTz+', '+business.wedEndtime+' '+business.wedEndTz+' #';
		
		}else{
			
			business.hours += 'wed- 00:00 AM, 00:00 PM #';
		}
		
		//for thu
		if((angular.isUndefined(business.thucb) || business.thucb==false) && (!(angular.isUndefined($scope.business.thuStrtime)) || !(angular.isUndefined($scope.business.thuEndtime))) ){
		//if(angular.isUndefined(Business.thucb) || Business.thucb==false ){
			business.thuStrtime         =  			  $scope.business.thuStrtime.value;
			business.thuEndtime         =             $scope.business.thuEndtime.value;
			business.hours += 'thu- '+business.thuStrtime+' '+business.thuStrTz+', '+business.thuEndtime+' '+business.thuEndTz+' #';
		
		}else{
			
			business.hours += 'thu- 00:00 AM, 00:00 PM #';
		}
		
	//	console.log(angular.isUndefined($scope.business.friStrtime));
		//console.log(angular.isUndefined($scope.business.friEndtime));
		//console.log(angular.isUndefined(Business.fricb));
		
		//for fri
		if((angular.isUndefined(business.fricb) || business.fricb==false) && (!(angular.isUndefined($scope.business.friStrtime)) || !(angular.isUndefined($scope.business.friEndtime))) ){
			business.friStrtime         =  			  $scope.business.friStrtime.value;
			business.friEndtime         =             $scope.business.friEndtime.value;
			business.hours += 'fri- '+business.friStrtime+' '+business.friStrTz+', '+business.friEndtime+' '+business.friEndTz+' #';
		
		}else{
			
			business.hours += 'fri- 00:00 AM, 00:00 PM #';
		}
		
		//for sat
		if((angular.isUndefined(business.satcb) || business.satcb==false) && (!(angular.isUndefined($scope.business.satStrtime)) || !(angular.isUndefined($scope.business.satEndtime))) ){
		//if(angular.isUndefined(Business.satcb) || Business.satcb==false ){
			business.satStrtime         =  			  $scope.business.satStrtime.value;
			business.satEndtime         =             $scope.business.satEndtime.value;
			business.hours += 'sat- '+business.satStrtime+' '+business.satStrTz+', '+business.satEndtime+' '+business.satEndTz+' #';
		
		}else{
			
			business.hours += 'sat- 00:00 AM, 00:00 PM #';
		}
		
		//for sun
		if((angular.isUndefined(business.suncb) || business.suncb==false) && (!(angular.isUndefined($scope.business.sunStrtime)) || !(angular.isUndefined($scope.business.sunEndtime))) ){
		//if(angular.isUndefined(Business.suncb) || Business.suncb==false ){
			business.sunStrtime         =  			  $scope.business.sunStrtime.value;
			business.sunEndtime         =             $scope.business.sunEndtime.value;
			business.hours += 'sun- '+business.sunStrtime+' '+business.sunStrTz+', '+business.sunEndtime+' '+business.sunEndTz+' #';
		
		}else{
			
			business.hours += 'sun- 00:00 AM, 00:00 PM #';
		}
		
		business.businessHours = business.hours;
		businessService.Business=business;		
		// businessService.photo=$scope.photo;
		console.log(business);
				
		   var uploadUrl='/UrbanLife/service/api/v1/business/add';
//		var uploadUrl='/UrbanLife/service/api/v1/business/add';
			var Businessstr=angular.toJson(business, false);
			var userstr=angular.toJson(user, false);
	        var fd = new FormData();
	        fd.append('photo', businessService.photo);
	        fd.append('business',Businessstr);
	        fd.append('other',cuisines);
	        fd.append('user',userstr);
	        $http.post(uploadUrl, fd, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	           
	        })
	        .success(function(){
	            console.log("sucess");
	            window.location = "#/welcomeoffer";
	        })
	        .error(function(){
	        	console.log("failure");
	        });
		
		
	}
		
}]);


/*CHanges for POpup*/


app.controller('ModalDemoCtrl',['$scope','$http', '$modal', '$log','cuisinesService', function ($scope,$http, $modal, $log, cuisinesService) {
	
	
	
		
		$scope.getdata  = function(){
			var Url='/UrbanLife/service/api/v1/business/cuisine';
	  	    $http.get(Url).
		        success(function(data) {
		                	
		        	/**/
		        	var allCuisines =data; 
		        	
		        	var selectedCuisines=[];
		        	 $scope.showItem="";
		        	for(var i=0; i<allCuisines.length; i++){
		        				        			
		        			if($.inArray(allCuisines[i].name, selectedCuisines)==-1){
		        				
		        				if($scope.showItem==""){
		        					$scope.showItem ='{"name":"'+allCuisines[i].name+'","checked":'+false+'}';
		        				}else{
		        					$scope.showItem +=',{"name":"'+allCuisines[i].name+'","checked":'+false+'}';
		        						        					
		        				}
			        		}else{
			        			if($scope.showItem==""){
			        				
			        				$scope.showItem ='{"name":"'+allCuisines[i].name+'","checked":'+true+'}';
			        				
			        			}else{
			        				
			        				$scope.showItem +=',{"name":"'+allCuisines[i].name+'","checked":'+true+'}';
			        			}
			        		}
		        			
		        		}
		        	
		        			        	
		        	 var cuisines = cuisinesService.Cuisines;
		        	 $scope.selected={};
		        	 $scope.open = function (size) {

		        	    var modalInstance = $modal.open({
		        	      templateUrl: 'cusines-popup.html',
		        	      controller: 'ModalInstanceCtrl',
		        	      size: size,
		        	      resolve: {
		        	        items: function () {
		        	        	//alert('Items alert'+JSON.stringify(cuisinesService.Cuisines));
		        	        	if(!(angular.isUndefined(cuisinesService.Cuisines.length))){
		        	        		$scope.items =  angular.fromJson('['+cuisinesService.Cuisines+']');
		        	        	}else{
		        	        		//alert('Items alert'+JSON.stringify($scope.showItem));
		        	        		$scope.items =  angular.fromJson('['+$scope.showItem+']');
		        	        		}
		        	        	
		        	           return $scope.items;
		        	        }
		        	      }
		        	    });
		        		
		        		
		        	    modalInstance.result.then(function (selectedItem) {
		        	    	
		        	    	cuisines = selectedItem;
		        	    	cuisinesService.Cuisines = cuisines;
		        	      $scope.selected = selectedItem;
		        	     
 						$scope.show = angular.fromJson('['+$scope.selected+']');
		        	    }, function () {
		        	      $log.info('Modal dismissed at: ' + new Date());
		        	    });
		        	  };
		        	 
		        	 
		            
		        }).error(function() {
		        	console.log("error ");
		        	
		        });
			
		}
		$scope.getdata();
		   
}]);

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

app.controller('ModalInstanceCtrl', function ($scope, $modalInstance, items ) {

  $scope.items = items;
  $scope.selected = {
    item: $scope.items[0]
  };
 $scope.resultArray=[];	
 $scope.cuisinesJson={};
 
 $scope.getValues=function(selected,event){
	 $scope.URSelected = "";
		 
	 for (var i = 0; i < $scope.items.length; i++) {
		
		 if(event.name ==  $scope.items[i].name){
			 
			     if(selected==true){
			    	 selected=false; 
			     }else{
			    	 selected=true; 
			     }
	            if ($scope.URSelected == "") {
	                $scope.URSelected ='{"name":"'+ event.name+'",'+'"checked":'+selected+'}';
	               
	            } else {
	                $scope.URSelected = $scope.URSelected + ', ' + '{"name":"'+event.name+'",'+'"checked":'+selected+'}';
	            }
	        }else{
	        	  if ($scope.URSelected == "") {
		                $scope.URSelected ='{"name":"'+ $scope.items[i].name+'",'+'"checked":'+$scope.items[i].checked+'}';
		            }else{     
		            	$scope.URSelected = $scope.URSelected + ', ' + '{"name":"'+ $scope.items[i].name+'",'+'"checked":'+$scope.items[i].checked+'}';
		            	}
	        }
		 
	    }
	 $scope.cuisinesJson = $scope.URSelected;
	
  }
 // connections.arr = $scope.resultArray;
  $scope.ok = function () {
    $modalInstance.close($scope.cuisinesJson);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});




})();
