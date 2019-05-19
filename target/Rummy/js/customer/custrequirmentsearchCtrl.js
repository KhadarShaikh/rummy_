resourceApp.controller('customerrequirementsearachCtrl',['$scope','RAService','$rootScope',"$stateParams",'$state',function($scope,RAService,$rootScope,$stateParams,$state){
//	  $scope.$on('$viewContentLoaded', function () {
//		  $scope.dynamicsearch();
//	   })
	        //pagination
	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 5; // Maximum number of items per page.	
	        
	var registrationId = localStorage.getItem('registrationId');
	  var primarySkills = $rootScope.primarySkills1;
	  var jobCategory = $rootScope.jobCategory;
	  var location =$rootScope.jobLocation;
	  var yearsOfExperience = $rootScope.experience;
	  var Resource_Id=$rootScope.requirementid;
	  debugger;
	  RAService.searchRequirementCustomer(primarySkills,jobCategory,location,yearsOfExperience,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
	 		debugger;
	 		//$scope.list = data.result;
	 		 $scope.list=data.allList;
	 		$scope.totalCount= data.count;
	 		$scope.errorMessage=data.errorMessage;
	 		
			   $scope.skillslist = data.allList;
 		console.log($scope.skillslist);	
	 		
	 	})
	  $scope.dynamicsearch = function(){
		  debugger;
			 //$scope.skil.skills="";$scope.job.jobCategory1="";$scope.exp2.totalExperience="";$scope.location2.city="";$scope.customer1.customer="";$scope.budget1.budget="";
			 if(primarySkills==""){
				 primarySkills=undefined;
			 }
			 if(jobCategory==""){
				 jobCategory=undefined;
			 }
			 if(location==""){
				 location=undefined;
			 }
			 if(yearsOfExperience==""){
				 yearsOfExperience=undefined;
			 }
			 if(primarySkills==undefined&&jobCategory==undefined&&location==undefined&&yearsOfExperience==undefined){
//				 $scope.postareq(); 
			 }
			 else{
				 RAService.searchRequirementCustomer(primarySkills,jobCategory,location,yearsOfExperience,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
					debugger;
					 $scope.errormsg=data.errorCode;
					 if($scope.errormsg=="NO_CONTENT"){
						 console.log("no data found");
						 $scope.divshowing=true;
						 $scope.divshowing1=false;
					 }
					 if($scope.errormsg=="OK"){
						 $scope.list = data.result;
						 //$scope.list=data.allList;
					   console.log($scope.list);
					   $scope.totalCount= data.count;
					   $scope.skillslist = data.allList;
					   $scope.divshowing1=true;
					   $scope.divshowing=false;				   
						$scope.divLoadPagination=false;
						$scope.divSearchPagination=true;
						$scope.divFilterPagination=false;
					 }
					
				   })			
			}
		 };
		 $scope.dynamicsearch();
	 	$scope.skills=["Java","jsp","Servlets","Spring","HTML","CSS","Bootstrap","Angularjs","Nodejs","Php","Phyton","MySQL","MongoDB","Oracle","SQL Server"];

	 	$scope.allListsfunc= function(){
		
	 		RAService.allreqLists().then(function(data){
	 	        $scope.skillslist = data.result;
	 	        console.log(" match with us "+$scope.skillslist);

	 	    });
	 		}
	 	
	 	$scope.pageChanged = function() {
	        $scope.edit()
	            console.log('Page changed to: ' + $scope.pageIndex);
	    };
	 	 $scope.pageChangedSearchPagination = function() {
	         $scope.dynamicsearch()
	             console.log('Page changed to: ' + $scope.pageIndex);
	     };
	     
	     $scope.pageChangedFilterPagination = function() {
	         $scope.checkbox()
	             console.log('Page changed to: ' + $scope.pageIndex);
	     };
	     
	     $scope.sidefilterlist=function()
	     {
	  	 
	  	   RAService.getsidefilterslist_customer().then(function(data) {
	                $scope.skillslist = data.result;
	 
	              
	                console.log($scope.skillslist);
	              
	             
	      }, function(err) {
	             if (err) {
	                    $scope.errorMessage = err;
	             }
	      })

	  	   
	     }
	 		
		
	 	 $scope.skil = {
	 			skills : []
	 		};
	 		$scope.job = {
	 			jobCategory1 : []
	 		};
	 		$scope.exp2 = {
	 			totalExperience : []
	 		};
	 		$scope.location2 = {
	 			city : []
	 		};
	 		$scope.customer1 = {
	 			customer : []
	 		};
	 		$scope.budget1 = {
	 			budget : []
	 		};
	 		
	 		$scope.checkbox = function() {
				
				$scope.search=[];
				if ($scope.skil.skills.length == 0) {
					$scope.skil.skills = "undefined";
				}
				if ($scope.job.jobCategory1.length == 0) {
					$scope.job.jobCategory1 = "undefined";
				}
				if ($scope.exp2.totalExperience.length == 0) {
					$scope.exp2.totalExperience = "undefined";
				}
				if ($scope.location2.city.length == 0) {
					$scope.location2.city = "undefined";
				}
				if ($scope.customer1.customer.length == 0) {
					$scope.customer1.customer = "undefined";
				}
				if ($scope.budget1.budget.length == 0) {
					$scope.budget1.budget = "undefined";
				}
				if(($scope.skil.skills == "undefined")&&($scope.job.jobCategory1 == "undefined")&&($scope.exp2.totalExperience == "undefined")&&($scope.location2.city == "undefined")
						&&($scope.customer1.customer == "undefined")&&($scope.budget1.budget == "undefined")){
					$scope.edit(); 
					
				}
				else{
				RAService.searchsidefilterrequirment(
						
						$scope.skil.skills,
						$scope.job.jobCategory1,
						$scope.location2.city,
						$scope.exp2.totalExperience,
						$scope.customer1.customer,
						$scope.budget1.budget).then(
						function(data) {
							
							 $scope.errormsg=data.errorCode;
							 if($scope.errormsg=="NO_CONTENT"){
								 console.log("no data found");
								/* $scope.skillslist= data.allList;*/
								 $scope.divshowing=true;
								 $scope.divshowing1=false;
							 }
							 if($scope.errormsg=="OK"){
								 $scope.resourcelist = data.result;
								 console.log($scope.resourcelist);
								
								// console.log($scope.skillslist);
								 $scope.totalCount = data.count;
								 $scope.skillslist= data.allList;
							   $scope.divshowing1=true;
							   $scope.divshowing=false;
								$scope.divLoadPagination=false;
								$scope.divSearchPagination=false;
								$scope.divFilterPagination=true;
							 }
							
						})

			}
			}
	        $scope.edit = function(){
	        	
	        	 RAService.searchRequirementCustomer(primarySkills,jobCategory,location,yearsOfExperience,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
	 				
					 $scope.errormsg=data.errorCode;
					 if($scope.errormsg=="NO_CONTENT"){
						 console.log("no data found");
						 $scope.divshowing=true;
						 $scope.divshowing1=false;
					 }
					 if($scope.errormsg=="OK"){
					   $scope.resourcelist = data.result;
					   console.log($scope.resourcelist);
					   $scope.totalCount= data.count;
					   $scope.skillslist= data.allList;
					   $scope.divshowing1=true;
					   $scope.divshowing=false;				   
						$scope.divLoadPagination=false;
						$scope.divSearchPagination=true;
						$scope.divFilterPagination=false;
					 }
					
				   })	
	}
	        
	        
	        $scope.requestResource = function(requirementId,Resource_Id){
	        	debugger;
	        	  var requirementid = $rootScope.requirementid;
	        	  //var resource_id=localStorage.getItem('vendor_resource_id');
                  var customer_registrationId = $rootScope.customer_registrationId;
                  //var resource_registrationid=localStorage.getItem('registrationId');
	        	var requirementId=requirementId;
	        	var Resource_Id = Resource_Id;
//	        	var resource_id=$rootScope.resource_id
	           /* $scope.vendorId = $rootScope.vendor_resource_id;
                $scope.registrationid=$rootScope.resource_registrationid;*/
	        	RAService.requestResourcevendor(requirementid,customer_registrationId,requirementId,Resource_Id).then(function(data){
	        		$scope.userdata = data.result; 
	        		alert("successfully Submitted");
	        	})
	        }
}]);


