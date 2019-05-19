resourceApp.controller("alluserlistCtrl",["$scope","$state","$stateParams","RAService",function($scope, $state, $stateParams, RAService){
//	$scope.$on('$viewContentLoaded', function () {
//	 	$scope.getUserDetails();
  //  });
	
	angular.element(document).ready(function () {
		$scope.getUserDetails();
	});
	
	
	// $scope.orderByField = 'firstname';
	  $scope.reverseSort = false;
	//pagination
	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.
	    
    $scope.getUserDetails = function(){
    	RAService.userlist($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
			$scope.userList = data.result;
			console.log($scope.userList);
			 $scope.totalCount = data.count;
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
     }
    
	 $scope.pageChanged = function() {
    $scope.getUserDetails()
        console.log('Page changed to: ' + $scope.pageIndex);
	 }
	 // User Status Functionality
	 
	 $scope.statusUsers = function(user){
		
			if(user.status == "Active"){
				user.status = "InActive";
			RAService.userStatus(user).then(function(data){
				$scope.user = data.result;
				console.log($scope.user);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}
			})
			} else {
				user.status = "Active";
				RAService.userStatus(user).then(function(data) {
					$scope.user = data.result;
					console.log($scope.user);
				}, function(err) {
					if (err) {
						$scope.errorMessage = err;
					}
				})
			}
	}
		
}]);


resourceApp.controller('alluseraddCtrl',["$scope","$state","$stateParams","RAService",function($scope,$state,$stateParams,RAService){
//	$scope.$on('$viewContentLoaded',function(){	
//		$scope.getroles();
//	})
	
		
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
	$scope.userstatus = ["Enable","Disable"];

	$scope.userall = function(){
		
		$scope.alluser.registrationId = localStorage.getItem('registrationId');
		$scope.alluser.registrationType = localStorage.getItem('registrationType');
		RAService.alluseradd($scope.alluser).then(function(data){
			$scope.alluseradd1 = data.result;
			console.log($scope.alluseradd1);
			$state.go('RA.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}])


resourceApp.controller('alluserupdateCtrl',["$scope","$state","$stateParams","RAService",function($scope, $state, $stateParams, RAService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.getalluser();
		$scope.getroles();
	})
	
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
			console.log($scope.alluser)
			localStorage.setItem('alluserid', $scope.alluser._id);
		})
	}

	$scope.alluserupdate = function(){
	
		
		
		$scope.alluser._id = localStorage.getItem('alluserid');
		RAService.alluserupdate($scope.alluser).then(function(data){
			$scope.qqq = data.result;
			console.log($scope.qqq);
			$state.go('RA.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
	
}])

