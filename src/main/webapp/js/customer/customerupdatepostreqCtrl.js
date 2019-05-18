resourceApp.controller('customerupdatepostreqCtrl',["$scope","$rootScope","$state","$stateParams","RAService","masterService",function($scope,$rootScope,$state,$stateParams,RAService,masterService){
    $scope.$on('$viewContentLoaded', function () {
		//$scope.postrequirement = {}; 
		$scope.edit();
		
        })
		
         $scope.stage = "";
    
    // Navigation functions
    $scope.next = function (stage) {
      if ($scope.frm.$valid) {
        $scope.direction = 1;
        $scope.stage = stage;
      
      }
    };

    $scope.back = function (stage) {
      $scope.direction = 0;
      $scope.stage = stage;
    };
  
      
     
	    $scope.jobRole = ["Fresher","Intern","Trainee","Junior Developer","Senior Developer","Project Lead"];
	    $scope.joblocation= ["Bangalore","Chennai","Hyderabad","Pune","Itanagar","Dispur","Patna","Raipur","Panaji","Gandhinagar","Punjab","Shimla","Srinagar","Ranchi",
        	"Thiruvananthapuram","Bhopal","Mumbai","Imphal","Shillong","Aizawl","Kohima","Bhubaneswar","Jaipur","Gangtok","Noida","Amaravathi","Agartala","Lucknow","Dehradun","Kolkata"];
        $scope.experience = ["1-2 years","2-3 years","3-5 years","5-7 years","7-10 years"];
		$scope.Rate=["Hourly","Per-Day","Per-Week","Per-Month",];
		$scope.skills=["java","jsp","servlets","Spring","Html","Css","Bootstrap","Angularjs","Nodejs","Php","Phyton","MySQL","MongoDB","Oracle","Sql Server"];
		
        $scope.yearsOfExperience = ["0 Year","1 Year", "2 Years", "3 Years","4 Years", "5 Years", "6 Years","7 Years", "8 Years", "9 Years","10 Years","11 Years"];
        $scope.monthsOfExperience = ["0 Month","1 Month", "2 Months", "3 Months","4 Months", "5 Months", " 6 Months","7 Months", "8 Months", "9 Months","10 Months","11 Months"];
        
        $scope.edit = function(){
        	debugger;
        	RAService.postareqGetById($stateParams.postId).then(function(data){
					$scope.postrequirement = data.result;
					$scope.postrequirement.primarySkills = $scope.postrequirement.primarySkills.toString();
					$scope.postrequirement.secondarySkills = $scope.postrequirement.secondarySkills.toString();
					 $scope.masterdetails();
					}),
					function(err){
						if(err){
							$scope.errorMessage = err;
						}else{
							$scope.errorMessage = err;
					   }   
					}
}
        
        $scope.masterdetails=function(){
        	debugger;
        	RAService.getmasterdetails().then(function(data) {
    	           debugger;                             
    	           		$scope.jobcategorylist_customer=data.result.jobCategory;
    	                $scope.jobtypelist_customer=data.result.jobType;
    	                $scope.primarySkills_customer=data.result.primarySkills;
    	                $scope.secondarySkills_customer=data.result.secondarySkills;
    	                $scope.noticelist=data.result.joinWithIn;
    	         })
    	    }

			$scope.saveDetails = function () {
				$scope.postrequirement.registrationId = $scope.comId;
				$scope.postrequirement.primarySkills = $scope.postrequirement.primarySkills.toString();
				$scope.postrequirement.secondarySkills = $scope.postrequirement.secondarySkills.toString();
				 $scope.postrequirement.registrationId = localStorage.getItem('registrationId');
			    RAService.updatepostareq($scope.postrequirement).then(function(data){
				$scope.postdata = data.result;
				console.log($scope.postdata);
				$state.go('customer.postrequirement');
				}),function(err){
						if(err){
							$scope.errorMessage = err;
						}else{
							$scope.errorMessage = err;
			           }   
			        }
			}; 


}]);
