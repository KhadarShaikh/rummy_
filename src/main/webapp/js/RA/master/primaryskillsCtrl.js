resourceApp.controller("primaryskillsController",["$scope","$state","masterService",function($scope, $state, masterService){
	
	    $scope.$on('$viewContentLoaded',function(){
		$scope.alluser = {};
		$scope.getprimaryskillslist();
	})
	
	   //pagination
	   $scope.maxSize = 2;     // Limit number for pagination display number.  
	   $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	   $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	   $scope.pageSizeSelected = 10; // Maximum number of items per page.
	   
		    $scope.getprimaryskillslist = function(){
			masterService.allprimaryskills($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
			$scope.primary = data.result;
			console.log($scope.UserDetails);
			$scope.totalCount = data.count;
			console.log("allprimaryskills.....................");
			console.log($scope.primary);
		})
		
	
	}
	$scope.getprimaryskillslist();
	$scope.pageChanged = function() {
	    $scope.getprimaryskillslist()
	        console.log('Page changed to: ' + $scope.pageIndex);
	};
	
	
}]);

resourceApp.controller('primaryskillsAddCtrl',["$scope","$state","$stateParams","masterService",function($scope,$state,$stateParams,masterService){
/*	$scope.$on('$viewContentLoaded',function(){
		$scope.newprimaryskills();
		
		
	})*/
	$scope.newprimaryskills = function(){
		debugger;
		masterService.saveprimaryskills($scope.alluser).then(function(data){
			$scope.primaryskills = data.result;
			console.log($scope.primaryskills);
			$state.go('RA.PrimarySkills');
			
		},function(err){
			
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}])
		
resourceApp.controller('primaryskillsupdateCtrl',["$scope","$state","$stateParams","masterService",function($scope, $state, $stateParams, masterService){
				$scope.$on('$viewContentLoaded',function(){
					$scope.getPrimaryskillsbyId();
					//$scope.jobtypeupdate();
					$scope.getroles;
				})
				

				// get data By Id 
				
				$scope.getPrimaryskillsbyId = function(){
					debugger;
					masterService.getPrimaryskillsById($stateParams.alluser).then(function(data){
						$scope.alluser = data.result;
						console.log($scope.alluser)
						
					})
				}
				
				$scope.primaryskillsupdate = function(){
					debugger;
					
					masterService.updatePrimaryskills($scope.alluser).then(function(data){
						$scope.qqq = data.result;
						console.log($scope.qqq);
						$state.go('RA.PrimarySkills');
					},function(err){
						if(err){
							$scope.errorMessage = err;
						}else{
							$scope.errorMessage = err;
			           }   
			        })
				}
				
			}])
		







