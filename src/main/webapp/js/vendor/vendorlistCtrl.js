resourceApp.controller('vendorlistCtrl',['$scope','$document','RAService',function($scope,$document,RAService){
	
	
	//$scope.$on('$viewContentLoaded', function () {
		//$scope.registration = {};
		//$scope.registrationlist();
	//	$scope.getRegister();
//		$scope.PlanGetById();
	//})
	angular.element(document).ready(function () {

		$scope.getRegister();
		$scope.PlanGetById();

	});
	
	
	$scope.value=false;
$scope.PreviousPlans=function(){
		$scope.value=true;
		$scope.showMe = false;
	}
	
	
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
$scope.PlanGetById=function(){
	$scope.id=localStorage.getItem('registrationId');
			RAService.GetPlanById($scope.id).then(function(data){
		       		$scope.planlist=data.result;
		       		$scope.noOfDays=data;
		       	
		       		console.log($scope.planlist);      		
		       		
		       },function(err){
		       if(err){
		           $scope.errorMessage = err; 
		      	 }
		       })	
			}
	
	
	
	
	
	$scope.showMe = false;
	$scope.msaList=function(){
		$scope.showMe = true;	
		$scope.value=false;
		debugger;
		var registrationId=	localStorage.getItem('registrationId');
		RAService.viewcustomermsa(registrationId).then(function(data){
		      $scope.msalist=data.result;
		      debugger;
		      console.log($scope.msalist);      	
		      
		      },function(err){
		      if(err){
		          $scope.errorMessage = err; 
		     	}
		      })	
		}
	
	
	
	
	
	
	
	
}]);