var resourceApp = angular.module('exampleApp', ['ui.router', 'ngCookies', 'ngResource','checklist-model','dx','ngSanitize','ngMessages','ui.bootstrap','oi.select'])
 



resourceApp.run(function ($rootScope, $location, $cookieStore, UserService) {

    /* Reset error when a new view is loaded */
    $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
       
    });
    $rootScope.$on('$stateChangeSuccess', function() { document.body.scrollTop = document.documentElement.scrollTop = 0; });

    $rootScope.hasRole = function (role) {

        if ($rootScope.user === undefined) {
            return false;
        }

        if ($rootScope.user.roles[role] === undefined) {
            return false;
        }

        return $rootScope.user.roles[role];
    };

//    $rootScope.logout = function () {
//        delete $rootScope.user;
//        delete $rootScope.accessToken;
//        $cookieStore.remove('accessToken');
//        $location.path("/login");
//    };

    /* Try getting valid user from cookie or go to login page */
    var originalPath = $location.path();
   //$location.path("/login");
    var accessToken = $cookieStore.get('accessToken');
    if (accessToken !== undefined) {
        $rootScope.accessToken = accessToken;
        UserService.get(function (user) {
            $rootScope.user = user;
            $location.path(originalPath);
        });
    }

    $rootScope.initialized = true;
});


resourceApp.controller('LoginController',['$scope','$location', '$rootScope', '$state', '$cookieStore', 'UserService', 'BlogPostService','RAService',function($scope,$location,$rootScope,$state, $cookieStore, UserService, BlogPostService,RAService){
    $scope.rememberMe = false;
 /*   $scope.$on('$viewContentLoaded', function () {
    	$scope.getplans();
    	//$scope.registration = {};
    })*/
    
       //pagination
        $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.
    $scope.createRegister = function () {
  		RAService.buynow($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
  			$scope.UserDetails = data.result;
  			 $scope.totalCount = data.count;
  			 if($scope.UserDetails.length !=0){
  				$location.path( "/Plans" );
  			 }
  			 else{
  		       $location.path( "/Register" );
  			 }
  		},function(err){
  			if(err){
  				$scope.errorMessage = err;
  			}
  		});

    }
    
    $scope.setErrorMessageToUndefined = function() {
        $scope.errorMessage="";
    };
    
    $scope.login = function () {
        if(!$scope.username){
        	 alert('Please Enter Email Id');
        }else if(!$scope.password){
        	alert('please Enter password');
            
        }
        else{
              UserService.authenticate($.param({
                    username: $scope.username,
                    password: $scope.password
                }), function (authenticationResult) {
            	
            	  //written by priyanka
            	  if(authenticationResult.result=="CONFLICT"){
            		  $scope.errorMessage = "Invalid username and password";
            		 // alert("your username and password combo is not matched");
            	  }
                    var accessToken = authenticationResult.result.token;
                    $scope.userName = authenticationResult.result.userName;
                   
                    $rootScope.accessToken = accessToken;
                    if ($scope.rememberMe) {
                        $cookieStore.put('accessToken', accessToken);
                    }
                    if(accessToken == undefined)
                    	{
                    	console.log("Your Password is incorrect");
                    	}
                    else
                    	{
                    BlogPostService.user($scope.userName).then(function(response){
                        $scope.user = response.data;
                        localStorage.setItem('loginusername',response.data.result.username)
                     
                        $rootScope.user=$scope.user;
                      
                                
                        $rootScope.name_user=$rootScope.user.result.username;
                        $rootScope.roles=$rootScope.user.result.registrationType;
                        console.log($rootScope.roles);
                        $scope.user.registrationType  = $scope.user.result.registrationType.split(',');
                      
                        localStorage.setItem('registrationType', $rootScope.roles);
                        localStorage.setItem('registrationId', $scope.user.result.registrationId);
                        localStorage.setItem('use', $scope.user.result.roles.USER);
                        localStorage.setItem('admi',$scope.user.result.roles.SUPERADMIN);
                        localStorage.setItem('firstName', $scope.user.result.firstname);
                      
                    // localStorage.getItem('registrationType');
                        
                      //checking roles and entering into dashboard
                        if( $rootScope.roles=='customer'){
                        	
                          $state.go('customer.dashboard');
                        }
                        if( $rootScope.roles=='vendor'){
                  
                         $state.go('vendor.dashboard');
                       }
                        if( $rootScope.roles=='RA'){
                        	
                           $state.go('RA.dashboard');
                          }
//                   
//                          if($scope.user.registrationType.length > 1 && $scope.user.registrationType[0] == "RA"){
//                            console.log("RA Module");
//                             $state.go('RA.dashboard');
//                          }
//                          if($scope.user.registrationType.length > 1 && $scope.user.registrationType[0] == "customer"){
//                             console.log("customer Module")
//                             $state.go('customer.dashboard');
//                          }
//                         if($scope.user.registrationType.length > 1 && $scope.user.registrationType[0] == "vendor"){
//                              console.log("customer Module")
//                              $state.go('customer.dashboard');
//                          }
//                        if($scope.user.registrationType.length == 1){
//                            if($scope.user.registrationType[0] == "RA"){
//                            console.log("RA Module");
//                            $state.go('RA.dashboard');
//                        }
//                        }
//                        if($scope.user.registrationType.length == 1){
//                            if($scope.user.registrationType[0] == "vendor"){
//                            $state.go('vendor.dashboard');
//                        }
//                        }
//                        
//                        if($scope.user.registrationType.length == 1){
//                            if($scope.user.registrationType[0] == "customer"){
//                            console.log("customer Module")
//                            $state.go('customer.dashboard');
//                        }
//                        } 
                        
                        
                    })
              }
                });
        }
      
    };
    
    $scope.showMe = false;
    $scope.showus=true;
    $scope.myFunc = function() {
        $scope.showMe = !$scope.showMe;
        $scope.showus=!$scope.showus;
    };
    
    $scope.showMe = true;
    $scope.showus=false;
    $scope.myFunct = function() {
        $scope.showMe = !$scope.showMe;
        $scope.showus=!$scope.showus;
    };
    $scope.allcompanieslist = function(){		
	debugger;
		RAService.companieslist1().then(function(data){		
			$scope.Users = data.result;	
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		});
	}
    $scope.allcompanieslist_customer=function (){
    	RAService.companieslist_customers().then(function(data){		
			$scope.Users = data.result;	
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		});
    }
/*	$scope.allcompanieslist1 = function(){
		debugger;
		RAService.companieslist1().then(function(data){	
			debugger;
			$scope.Users = data.result;	
			console.log(data);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		});
	}*/
    
    
	$scope.getplans = function(){
		RAService.planlist().then(function(data){
		
			$scope.UserDetails = data.result;
			
	
			
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		});
	}
	
	//planid
	$scope.plan=function(bid){
					
					localStorage.setItem('planid',bid);
				
				}
	
	//registration
	$scope.companytype = ["Public Limited Company","Private Limited Company","Partnership","Proprietary"];
	$scope.quality = ["ISO 9001","ISO 9002","ISO I400","NONE"];
	$scope.registrationtype = ["Customer","Vendor"];
	$scope.Licences = ['1','2','3','4','5'];
	$scope.Period = ['1','2','3','4','5'];
	
	$scope.addData = function(registration){
		// spinner
		     $scope.loading = true;
       
			debugger;
			if($scope.registration.sowUser==undefined)
				{
				 $scope.registration.sowUser=$scope.registration.sowUser;
				}
			else{
				$scope.registration.sowUser=$scope.registration.sowUser.toString();
			}	    
			   if($scope.registration.registrationType==undefined){
				   $scope.registration.registrationType = $scope.registration.registrationType;
			   }
			   else{
				   $scope.registration.registrationType = $scope.registration.registrationType.toString();
			   }		
		$scope.registration.planId = localStorage.getItem('planid');		
		RAService.saveRegistration($scope.registration).then(function(data){
			$scope.dddd = data.result;
			 if(data.status==='Success'){
				alert('Registration Successfull')
				$scope.loading = false;
				$state.go('login');
			}
			else{
				$scope.loading = false;
				alert('Already registered with same email id please provide different email id')
				
				$state.go('login');
			}
			
			
		},function(err){    
			
			   
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
			}
		});
	}
	
	
	
    
}])


resourceApp.controller('LoginCtrl',["$scope","$rootScope","$location",function($scope,$rootScope,$location){
        $scope.logout = function(){
            localStorage.clear();
            delete $rootScope.roles;
            $location.path('/');
        }
}])

resourceApp.factory('UserService', function ($resource) {

    return $resource('rest/user/:action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action': 'authenticate'},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    
    );
});

resourceApp.factory('BlogPostService', function ($resource,$http) {

//    return $resource('rest/blogposts/:id', {id: '@id'});
	
    var obj = {};
    obj.user = function(userName){
        return $http.get('rest/user/userDetails/' +userName);
    }
    return obj;
});

//plans

resourceApp.controller('buynowCtrl',['$scope','RAService',function($scope,RAService){
	$scope.$on('$viewContentLoaded', function () {
		$scope.getplans();
	})
	
	$scope.getplans = function(){
		RAService.buynow().then(function(data){
			
			$scope.UserDetails = data.result;
		
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		});
	}

}])

angular.module('exampleApp').controller
  ( 'paymentCtrl'
  , function($scope,$locale) {
      $scope.currentYear = new Date().getFullYear()
      $scope.currentMonth = new Date().getMonth() + 1
      $scope.months = $locale.DATETIME_FORMATS.MONTH
      $scope.card = {type:undefined}
      $scope.save = function(data){
        if ($scope.paymentForm.$valid){
       
        }
      }
    }
  )
  

