resourceApp.controller("JoinWithInCtrl",["$scope","$state","$stateParams","masterService",function($scope, $state,$stateParams,masterService){
	   
	    $scope.$on('$viewContentLoaded', function () {
		$scope.JoinWithIn();

    })
		   //pagination
		   $scope.maxSize = 2;     // Limit number for pagination display number.  
		   $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
		   $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
		   $scope.pageSizeSelected = 10; // Maximum number of items per page.
	   
	         $scope.JoinWithIn = function(){
	        	 debugger;
		     masterService.joinwithinlist($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
			 $scope.joinwithinlist = data.result;
			 console.log($scope.UserDetails);
		     $scope.totalCount = data.count;
		      console.log("joinwithinlist.....................");
			 console.log($scope.joinwithinlist);
		})
	}
	    
	      $scope.JoinWithIn();
		  $scope.pageChanged = function() {
		  $scope.JoinWithIn()
		  console.log('Page changed to: ' + $scope.pageIndex);
		}
	
	    $scope.savenewJoin=function(){
	    	debugger;
		masterService.createnewJoin($scope.newJoin).then(function(data){
			$scope.allnewJoin = data.result;
			console.log($scope.allnewJoin);
			$state.go('RA.JoinWithIn');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}]);

resourceApp.controller("JoinWithInUpdateCtrl",["$scope","$state","$stateParams","masterService",function($scope, $state,$stateParams,masterService){
		$scope.$on('$viewContentLoaded', function () {
			$scope.joinwithinById();
	    })
	    
	$scope.joinwithinById = function(){
		//$scope.resource.registrationId = $scope.comId;
		//debugger;
		masterService.joinwithingetById($stateParams.joinWithIn).then(function(data){
			$scope.newJoin = data.result;
			console.log($scope.newJoin);
		})
	}
	
	$scope.updatejoinWithIn=function(){
		//debugger;
		masterService.updateJoin($scope.newJoin).then(function(data){
			$scope.newJoin = data.result;
			console.log($scope.newJoin);
			$state.go('RA.JoinWithIn');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	

}]);