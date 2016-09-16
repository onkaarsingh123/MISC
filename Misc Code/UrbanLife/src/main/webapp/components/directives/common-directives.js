app.directive('fileModel', ['$parse','$http', function ($parse,$http) {

       
       var http=$http;
       
    return {
        restrict: 'A',
        
        link: function(scope,element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            

            
            element.bind('change', function(){
              
              var imgtype=['image/jpeg','image/jpg','image/png','image/gif'];
              var fd = new FormData();
              var file=null;
            
             var uploadUrl='/UrbanLife/service/api/v1/merchants/upload';
          
              
              if(imgtype.indexOf(element[0].files[0].type)==-1)
                     {
                     scope.imageInvalide=true;
                     scope.showImg=false;
                     scope.filename=null;
                    }
              else
                     {
                    
                     scope.imageInvalide=false;
                     scope.showImg=true;
                     file = element[0].files[0];
                     fd.append('photo', file);
                     
                     
                     http.post(uploadUrl, fd, {
                     transformRequest: angular.identity,
                     headers: {'Content-Type': undefined}
                    
                 })
                 .success(function(data){
                     console.log("sucess"+data);
                     scope.path=data;
                     scope.filename=element[0].files[0].name;
                     
                    
                     
                    })
                 .error(function(data){
                     console.log("failure"+data);
                     
                 });
          
          
                     }
              
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
                
            });
            
            
            
        }
    };
}]);







app.directive("passwordVerify", function() {
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
