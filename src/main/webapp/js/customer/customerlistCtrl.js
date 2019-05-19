resourceApp.controller('customerlistCtrl',['$scope','$document','RAService',function($scope,$document,RAService){
	//$scope.$on('$viewContentLoaded', function () {
	//	$scope.getRegister();
	//})
	
angular.element(document).ready(function () {

		$scope.getRegister();
		$scope.PlanGetById();
//		$scope.msalist={};
	});
	
	
	$scope.getRegister = function() {
		$scope.id=localStorage.getItem('registrationId');
		RAService.getRegistrationById($scope.id).then(
				function(data) {
					$scope.registration = data.result;
					console.log($scope.registration);
					
				}, function(err) {
					if (err) {
						$scope.errorMessage = err;
					}
				})
	}
	
	$scope.showMe = false;
	$scope.msaList=function(){
		 $scope.showMe = true;	
		 $scope.previousplanshowMe = false;
		var registrationId=	localStorage.getItem('registrationId');
		RAService.viewcustomermsa(registrationId).then(function(data){
		      $scope.msalist=data.result;
		     
		      console.log($scope.msalist);      	
		      
		      },function(err){
		      if(err){
		          $scope.errorMessage = err; 
		     	}
		      })	
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
	
	$scope.previousplanshowMe = false;
	$scope.PreviousPlans=function(){
		 $scope.previousplanshowMe = true;
		 $scope.showMe = false;
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
	
	
	
	var user = localStorage.getItem('use');
	var admin =localStorage.getItem('admi');
	$scope.vvv = localStorage.getItem('registrationType');
	$scope.id=localStorage.getItem('registrationId');
	$scope.register =  $scope.vvv.split(',');
	$scope.user1 = localStorage.getItem('user')
	console.log($scope.user1)
	console.log($scope.register);
	$scope.model = "Customer";

	$scope.dataregister = function(){
		
		if($scope.registerData == "RA"){
			$state.go('RA.dashboard');
		}
		if($scope.registerData == "vendor"){
			$state.go('vendor.dashboard');
		}
		if($scope.registerData == "customer"){
			$state.go('customer.dashboard');
		}			  
	}
	
   if(admin== "true"){
		$scope.all_users_type=true;
	}else if(user == "true" && admin == "true"){
	    $scope.all_users_type=true;
	}
	else if(user == "true"){
		$scope.all_users_type=false;
	}
	else{

	}  
	
	
	
}]);