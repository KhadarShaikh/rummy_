resourceApp.controller('customerbuynewCtrl',['$scope','RAService','$stateParams','$state',function($scope,RAService,$stateParams,$state){
	$scope.$on('$viewContentLoaded',function(){
		$scope.plan();		
	})	
	
		$scope.plan=function()		
		{	
		RAService.changeplan($stateParams.planid).then(function(data){
					debugger;
					$scope.plandetails = data.result;
					debugger;
					console.log($scope.plandetails);
					
				},function(err){
					if(err){
						$scope.errorMessage = err;
					}
				});
		}
	// payment years code
	 var year = new Date().getFullYear();
     var range = [];
     range.push(year);
     for (var i = 1; i < 13; i++) {
         range.push(year + i);
     }
     $scope.years = range;
     console.log($scope.years);
     
     //months code
     $scope.validMonth=["January","February","March","April","May","June","July","August","September","October","November","December"];
}])
resourceApp.controller('customerbuynowCtrl',['$scope','RAService','$stateParams','$state',function($scope,RAService,$stateParams,$state){
	$scope.$on('$viewContentLoaded',function(){
		
		$scope.getplans();
	})
	
	$scope.getplans = function(){
		RAService.vendorbuynow().then(function(data){			
			$scope.UserDetails = data.result;
			console.log($scope.UserDetails);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		});
	}
}]);
resourceApp.filter( 'range', function() {
    var filter = 
      function(arr, lower, upper) {
        for (var i = lower; i <= upper; i++) arr.push(i)
        return arr
      }
    return filter
  }
);
resourceApp.controller( 'paymentCtrl', function($scope,$locale) {
	 debugger;
     var year = new Date().getFullYear();
     var range = [];
     range.push(year);
     for (var i = 1; i < 13; i++) {
         range.push(year + i);
     }
     $scope.years = range;

  }
);

	/*resourceApp.controller('paymentCtrl',['$scope','RAService','$stateParams','$state',function($scope,RAService,$stateParams,$state){

	$scope.validMonth=["January","February","March","April","May","June","July","August","September","October","November","December"];
	$scope.validYear=["2016","2017","2018","2019","2020","2021","2022","2023","2024"];
	$scope.cardType=["Visa","Maestro","Rupay","MasterCard"];
	
	$scope.cardDetails=function(card)	
	{
		$scope.card.registrationId=localStorage.getItem('registrationId');		
		RAService.paymentcarddetails(card).then(function(data){
		
					$scope.carddetails = data.result;		
					alert("OTP sent to Your Mobile ");
					$state.go("customer.dashboard")
					
				},function(err){
					if(err){
						$scope.errorMessage = err;
					}
				});
	}
		
	}])*/


