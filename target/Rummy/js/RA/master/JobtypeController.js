resourceApp.controller("JobtypeController",["$scope","$state","masterService",function($scope, $state, masterService){
	 
	$scope.$on('$viewContentLoaded',function(){
		$scope.alluser = {};
		$scope.getjobtypelist();
		$scope.deleteJobtype();
	})
	
	   //pagination
	   $scope.maxSize = 2;     // Limit number for pagination display number.  
	   $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	   $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	   $scope.pageSizeSelected = 10; // Maximum number of items per page.
	   
	     $scope.getjobtypelist = function(){
	    	 debugger;
		 masterService.newjobtype($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
		 $scope.newjobtype = data.result;
		 console.log($scope.UserDetails);
		 
	     $scope.totalCount = data.count;
	     console.log("newjobtype.....................");
		 console.log($scope.newjobtype);
	});
	}
	
	  $scope.getjobtypelist();
	  $scope.pageChanged = function() {
	  $scope.getjobtypelist()
	  console.log('Page changed to: ' + $scope.pageIndex);
	}

		 $scope.deleteJobtype= function(id){
			 masterService.JobtypedeleteRow(id).then(function(data){
					$scope.Jobtype = data.result;
					console.log($scope.status);
					$scope.status = data.status; 
					$scope.errorMessage = data.errorMessage; 
					if($scope.status == "Success"){
						alert($scope.errorMessage);
						$scope.getjobtypelist();
					}else{
						alert($scope.errorMessage);
						
			       } 
					/*$state.transitionTo($state.current, $stateParams, {
					    reload: true,
					    inherit: false,
					    notify: true
					});*/
			})
		};
		
}]);


resourceApp.controller('jobtypeaddCtrl',["$scope","$state","$stateParams","masterService",function($scope,$state,$stateParams,masterService){
	
	$scope.newjobtype = function(){
		debugger;
		masterService.saveNewJobtype($scope.alluser).then(function(data){
			$scope.newjobtype1 = data.result;
			console.log($scope.newjobtype1);
			$state.go('RA.Jobtype');
		},function(err){
			
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}])

resourceApp.controller('jobtypeupdateCtrl',["$scope","$state","$stateParams","masterService",function($scope, $state, $stateParams, masterService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.getjobtypebyId();
		$scope.jobtypeupdate();
		
	})
	

	// get data By Id 
	
	$scope.getjobtypebyId = function(){
		debugger;
		masterService.getJobtypeById($stateParams.alluser).then(function(data){
			$scope.alluser = data.result;
			console.log($scope.alluser)
			
		})
	}
	
	$scope.jobtypeupdate = function(){
		debugger;
		
		masterService.updateJobtype($scope.alluser).then(function(data){
			$scope.qqq = data.result;
			console.log($scope.qqq);
			$state.go('RA.Jobtype');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
}])