resourceApp.controller('vendoruserlistCtrl',["$scope","$state","$stateParams","RAService",function($scope,$state,$stateParams,RAService){
	
//	$scope.$on('$viewContentLoaded', function () {
//	 	$scope.getUserDetails();
 //   });
	angular.element(document).ready(function () {
		$scope.getUserDetails();
	});
	
	
	
	//pagination
	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 5; // Maximum number of items per page.
	    
    $scope.getUserDetails = function(){
 	   $scope.isLoading=true;
    
    	RAService.vendoruserlist($scope.pageIndex,$scope.pageSizeSelected).then(function(data){
			$scope.userList = data.result;
			   $scope.isLoading=false;
			console.log($scope.userList);
			$scope.totalCount = data.count;
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
    }
    debugger;
   $scope.allusersearch=function(){
	   if($scope.search1==undefined){
		   $scope.getUserDetails(); 
	   }
    RAService.search($scope.pageIndex,$scope.search1,$scope.pageSizeSelected).then(function(data){
    	debugger;
    	$scope.userList= data.result;
    	console.log("searchlist"+$scope.search);
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

resourceApp.controller('vendoruseraddCtrl',["$scope","$state","$stateParams","RAService",function($scope,$state,$stateParams,RAService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.alluser = {};
		$scope.getroles();
	})

	   $scope.checkBoxes = [
	                       {label: 'ADMIN', isChecked: false},
	                       {label: 'SUPERADMIN', isChecked: false},
	                       {label: 'USER', isChecked: false}
	                      
	                   ]; 
	 // $scope.isChecked = function() {
	//       for(var e in $scope.checkBoxes) {
	   //         var checkBox = $scope.checkBoxes[e];
	  //         if(checkBox.isChecked)
	     //          return true;
	    //   }
	    //   return false;
	 //  };
	
	var checkboxes = $("input[type='checkbox']"),
    submitButt = $("input[type='submit']");
	checkboxes.click(function() {
	    submitButt.attr("disabled", !checkboxes.is(":checked"));
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
	
/*	$scope.checkfun = function(){
	var checkboxes = $("input[type='checkbox']"),
    submitButt = $("input[type='submit']");

	checkboxes.click(function() {
    submitButt.attr("disabled", !checkboxes.is(":checked"));
});
	}*/
	
	

	$scope.userall = function(){
		debugger;
		$scope.alluser.registrationId = localStorage.getItem('registrationId');
		$scope.alluser.registrationType = localStorage.getItem('registrationType');
		RAService.alluseradd($scope.alluser).then(function(data){
			$scope.alluseradd1 = data.result;
			console.log($scope.alluseradd1);
			$state.go('vendor.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
}]);

