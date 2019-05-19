/*resourceApp.controller('jobgetCtrl',['$scope','masterService',function($scope,masterService) {	
	$scope.$on('$viewContentLoaded', function () {
		//$scope.registration = {};
		$scope.getJobCategory();
	})
	   //pagination
	   $scope.maxSize = 2;     // Limit number for pagination display number.  
	   $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	   $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	   $scope.pageSizeSelected = 5; // Maximum number of items per page.
	   
	  $scope.getJobCategory = function(){
		  debugger;
	  masterService.jobCategoryget($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
      $scope.category = data.result;
      console.log($scope.UserDetails);      
      $scope.totalCount = data.count;
      console.log("jobCategoryget.....................");
      console.log($scope.category);
  })
  
	  }
	  $scope.getJobCategory();
	  $scope.pageChanged = function() {
		  debugger;
	  $scope.getJobCategory()
	  console.log('Page changed to: ' + $scope.pageIndex);
	}
  
}]);*/

resourceApp.controller("jobgetCtrl",['$scope','masterService', function($scope,masterService) {
	$scope.$on('$viewContentLoaded', function () {
		$scope.getJobCategory();
	})
	   //$scope.filteredjob = [];
	  //$scope.jobList = [];
	  $scope.totalCount = 0  ;
	  $scope.pageIndex = 1;
	  $scope.pageSizeSelected = 10;
	  $scope.maxSize = 2;

	  $scope.getJobCategory = function() {
	   debugger;
	    masterService.jobCategoryget($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
	    	 $scope.jobList = data.result;
	    	 $scope.totalCount = data.count;
	    })
	    
	  }
	
	  $scope.$watch("currentPage + numPerPage", function() {
	    var begin = (($scope.pageIndex - 1) * $scope.pageSizeSelected)
	    , end = begin + $scope.pageSizeSelected;

	    $scope.filteredjob = $scope.jobList.slice(begin, end);
	  });
	}]);

resourceApp.controller('postCtrl',['$scope','$state','masterService',function($scope,$state,masterService){
	$scope.postjob = function(){
		masterService.jobCategorypost($scope.postcategory).then(function(data){
         $scope.postresult = data.result;
         $state.go('RA.jobcategory');
         console.log("postresult...............");
         console.log($scope.postresult);
      },function(err){    
		if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
			}
		});
}
}]);

resourceApp.controller('putCtrl',['$scope','$state','$stateParams','masterService',function($scope,$state,$stateParams,masterService){
	$scope.$on('$viewContentLoaded', function () {	
			   $scope.getId();
			  })

	$scope.getId = function(){
		masterService.jobCategoryById($stateParams.putcategory).then(function(data){
				$scope.putdata = data.result;
			console.log($scope.putdata);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			})
		}

		$scope.putjob = function(){
			masterService.jobCategoryPut($scope.putdata).then(function(data){
				$scope.dddd = data.result;
				console.log($scope.dddd);
				$state.go('RA.jobcategory');
			},function(err){    
					   
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			});
		}
	}])
