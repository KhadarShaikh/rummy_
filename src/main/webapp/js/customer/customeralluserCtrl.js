resourceApp.controller('customeruserlistCtrl',["$scope","$state","$stateParams","RAService",function($scope,$state,$stateParams,RAService){
	
	//$scope.$on('$viewContentLoaded', function () {
//	 	$scope.getUserDetails();
   // });
	angular.element(document).ready(function () {
		$scope.getUserDetails();
	});
	
	
    	 $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 5; 
	     
	    
    $scope.getUserDetails = function(){  
    	$scope.isLoading = true;
    /*	$scope.local = localStorage.getItem('registrationId');	*/
    	RAService.customeruserlist($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
			$scope.userList = data.result;
			 $scope.totalCount = data.count;
			console.log($scope.userList);
			$scope.isLoading = false;
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
    }
    $scope.allusersearch=function(){
    	if($scope.search2==undefined){
    		 $scope.getUserDetails();
    	}
        RAService.search($scope.pageIndex,$scope.search2,$scope.pageSizeSelected).then(function(data){
        
        	$scope.userList= data.result;
        	console.log("searchlist1"+$scope.search);
        	$scope.totalCount = data.count;
        },function(err){
        	if(err){
        		$scope.errorMesage = err;
        	}
        })
       }

    $scope.pageChanged = function() {
    	$scope.getUserDetails()
            console.log('Page changed to: ' + $scope.pageIndex);
    };
		
}]);

resourceApp.controller('customeruseraddCtrl',["$scope","$state","$stateParams","RAService",function($scope,$state,$stateParams,RAService){
	
	//$scope.$on('$viewContentLoaded',function(){
	
	//	$scope.getroles();
	//})
	angular.element(document).ready(function () {
		$scope.getroles();
	});
	
	$scope.getroles = function(){
		RAService.allusergetroles().then(function(data){
			$scope.alluser1 = data.result;
			console.log($scope.alluser1);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
	}
	
	$scope.enable=function(){
	
		var checkboxes = $("input[type='checkbox']"),
	    submitButt = $("input[type='submit']");

	checkboxes.click(function() {
	    submitButt.attr("disabled", !checkboxes.is(":checked"));
	});
	}
	
	$scope.userall = function(){
	
		$scope.alluser.registrationId = localStorage.getItem('registrationId');
		$scope.alluser.registrationType = localStorage.getItem('registrationType');
		RAService.alluseradd($scope.alluser).then(function(data){
			$scope.alluseradd1 = data.result;
			console.log($scope.alluseradd1);
			$state.go('customer.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
	
	
	
	
}]);


resourceApp.controller('customer1updateCtrl',["$scope","$state","$stateParams","RAService",function($scope, $state, $stateParams, RAService){
	angular.element(document).ready(function () {
		$scope.getalluser();
		
	});
	$scope.getroles = function(){
		RAService.allusergetroles().then(function(data){
			$scope.alluser1 = data.result;
			console.log($scope.alluser1);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
	}
	
	$scope.userstatus = ["Active","Inactive"];
	$scope.getalluser = function(){
		RAService.allusergetbyid($stateParams.alluserId).then(function(data){
			$scope.alluser = data.result;
			localStorage.setItem('alluserid', $scope.alluser._id);
			$scope.getroles();
		})
	}
	
	$scope.alluserupdate = function(){
	
		$scope.alluser._id = localStorage.getItem('alluserid');
		RAService.alluserupdate($scope.alluser).then(function(data){
			$scope.qqq = data.result;
			console.log($scope.qqq);
			$state.go('customer.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
}])


