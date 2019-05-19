resourceApp.controller('RACtrl',['$scope','$state','BlogPostService','RAService',function($scope,$state,BlogPostService,RAService){
	   $scope.reverseSort = false;
		var user = localStorage.getItem('use');
		var admin =localStorage.getItem('admi');
		$scope.loginusername=localStorage.getItem('loginusername');
	    
		var FirstName=localStorage.getItem('firstName');
		$scope.adminName=FirstName;
		
	   $scope.vvv = localStorage.getItem('registrationType');
	
		$scope.register =  $scope.vvv.split(',');
		console.log($scope.register);
		$scope.model = "RA";
		$scope.dataregister = function(){		
			if($scope.registerData == "RA"){
				$state.go('RA.dashboard');
			}
			if($scope.registerData == "vendor"){
				$scope.model = "vendor";
				$state.go('vendor.dashboard');
			}
			if($scope.registerData == "customer"){
				$scope.model = "customer";
				$state.go('customer.dashboard');
			}
			
		}
		


		
		
		$scope.PlanGetById=function(){			
		var registrationId=	localStorage.getItem('registrationId');
			RAService.GetPlanById(registrationId).then(function(data){
		       		$scope.planlist=data.result;
		       		$scope.noOfDays=data;
		       		
		       		console.log($scope.planlist);
		       		
		       		
		       },function(err){
		       if(err){
		           $scope.errorMessage = err; 
		      	 }
		       })	
			}	
		$scope.PreviousPlans=function(){
		
		var registrationId=	localStorage.getItem('registrationId');
			RAService.GetPreviousplan(registrationId).then(function(data){
		       		$scope.previousplanlist=data.result;
		       		
		       		console.log($scope.previousplanlist);      		
		       		
		       },function(err){
		       if(err){
		           $scope.errorMessage = err; 
		      	 }
		       })	
			}
		
	
}])