resourceApp.controller("secondaryskillCtrl",["$scope","masterService",function($scope,masterService){
	
	   //pagination
	   $scope.maxSize = 2;     // Limit number for pagination display number.  
	   $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	   $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	   $scope.pageSizeSelected = 10; // Maximum number of items per page.
	
	    $scope.getSeconderySkills = function(){
	    	debugger;
	    masterService.secondaryget($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
		$scope.secondaryget = data.result;
		console.log($scope.UserDetails);
		$scope.totalCount = data.count;
        console.log("secondaryget.....................");
        console.log($scope.secondaryget);
	})
	   }
	   $scope.getSeconderySkills();
	   $scope.pageChanged = function() {
		    $scope.getSeconderySkills()
		        console.log('Page changed to: ' + $scope.pageIndex);
		};
	
}]);
resourceApp.controller("secondaryaddCtrl",["$scope",'$state',"masterService",function($scope,$state,masterService){	
	$scope.secondaryadd = function(){
		masterService.secondaryadd($scope.secondadd).then(function(data){
         $scope.secondaryadd = data.result;
         $state.go('RA.secondaryskill');
         console.log("secondaryadd...............");
         console.log($scope.secondaryadd);
      },function(err){    
		if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
			}
		});
}
}]);
resourceApp.controller('secondaryeditCtrl',['$scope','$state','$stateParams','masterService',function($scope,$state,$stateParams,masterService){
	$scope.$on('$viewContentLoaded', function () {	
			   $scope.secondarygetId();
			  })

	$scope.secondarygetId = function(){
		masterService.secondaryById($stateParams.putskill).then(function(data){
				$scope.dataput = data.result;
			console.log($scope.dataput);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			})
		}

		$scope.skillput = function(){
			masterService.secondaryedit($scope.dataput).then(function(data){
				$scope.dddd = data.result;
				console.log($scope.dddd);
				$state.go('RA.secondaryskill');
			},function(err){    
					   
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			});
		}


	}]);