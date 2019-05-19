resourceApp.controller('customerCtrl',['$scope','$state','RAService',function($scope,$state,RAService){
	//	$scope.$on('$viewContentLoaded', function () {
	//		$scope.getUserDetails();
	//	})
	angular.element(document).ready(function () {
 
		$scope.getUserDetails();

	});
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
       
       $scope.getUserDetails = function() {
   		$scope.id=localStorage.getItem('registrationId');
   		RAService.getRegistrationById($scope.id).then(
   				function(data) {
   					$scope.registration = data.result;							
   				}, function(err) {
   					if (err) {
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
		//In Active plans list Ctrl
		
		
		$scope.showMe = false;
		$scope.PreviousPlans=function(){
			 $scope.showMe = !$scope.showMe;
		
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