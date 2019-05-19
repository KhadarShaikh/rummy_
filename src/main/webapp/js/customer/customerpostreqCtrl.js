resourceApp.controller('customerpostareqCtrl',['$scope','RAService','$rootScope','$state',function($scope,RAService,$rootScope,$state){    

//	$scope.$on('$viewContentLoaded', function () {
		//$scope.postareq();
		/*$scope.sidefilterlist();*/
	//})
	angular.element(document).ready(function () {

		$scope.postareq();
	//	$scope.sidefilterlist();

	});

	$scope.skills = [ "Java", "jsp", "Servlets",
		"Spring", "HTML", "CSS", "Bootstrap",
		"AngularJs", "Nodejs", "Php", "Phyton",
		"MySQL", "MongoDB", "Oracle", "SQL Server" ];

	$scope.Selectors = [ "skills", "totalExperience",
			"availability" ];
	$scope.SelectedCriteria = "";
	$scope.filterValue = "";
	
	$scope.allListsfunc= function(){
 		RAService.requirementlistaccordion().then(function(data){
 		   $scope.skillslist = data;
 	        console.log($scope.list);
 	    });
 		}
		
	//pagination
	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected =5; // Maximum number of items per page.	
	   
	$scope.postareq = function(){
		$scope.isLoading = true;
		
		$scope.divshowing1=true;
		$scope.divshowing=false;
		$scope.divLoadPagination=true;
		$scope.divSearchPagination=false;
		$scope.divFilterPagination=false;
		$scope.search=[];
		$scope.skil.skills="";$scope.job.jobCategory1="";$scope.exp2.totalExperience="";$scope.location2.city="";$scope.customer1.customer="";$scope.budget1.budget="";
		$scope.local = localStorage.getItem('registrationId');
		RAService.customeraddrequirment($scope.local,$scope.pageIndex,$scope.pageSizeSelected).then(function(data) {
			
	        $scope.list = data.result.mapperList;
	        $scope.skillslist = data.result.sideMapper;
	        $scope.errorMessage=data.errorMessage
	       $scope.isLoading = false;
	       
	        console.log($scope.list);
	        $scope.totalCount = data.count;
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
		
	}

	/*
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
	 */
	// $scope.sidefilterlist();
	 
	$scope.pageChanged = function() {
        $scope.postareq()
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
	
	$scope.requirement = function(postrequirement){
		
		if(postrequirement.status == "Active"){
			postrequirement.status = "Inactive";
		RAService.requirementStatus(postrequirement).then(function(data){
			$scope.aaaa = data.result;
			console.log($scope.aaaa);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
		}else{
			postrequirement.status = "Active";
		RAService.requirementStatus(postrequirement).then(function(data){
			$scope.aaaa = data.result;
			console.log($scope.aaaa);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
		}	
	}
	$scope.searchresource = function(_id,primarySkills,jobCategory,jobLocation,experience,registrationId) {
		
		
		$rootScope.requirementid=_id;
		$rootScope.experience=experience;
 		$rootScope.primarySkills1=primarySkills;
 		$rootScope.jobCategory=jobCategory;
 		$rootScope.jobLocation=jobLocation;
 		$rootScope.customer_registrationId=registrationId;

	$state.go('customer.resourcesearch');
	}

/*	
	$scope.dynamicsearch = function(primarySkills,jobCategory,jobLocation,experience){
		debugger;
		 RAService.searchrequirementbyid(primarySkills,jobCategory,jobLocation,experience).then(function(data){
			 debugger;
			   $scope.list = data;
			   console.log("this is list");
			   console.log($scope.list);
		   })			
	}*/
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
				$scope.postareq(); 
				
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
							 $scope.list = data.result;
							 console.log($scope.list);
							 /*$scope.skillslist= data.allList;*/
							// console.log($scope.skillslist);
							 $scope.totalCount = data.count;
						   $scope.divshowing1=true;
						   $scope.divshowing=false;
							$scope.divLoadPagination=false;
							$scope.divSearchPagination=false;
							$scope.divFilterPagination=true;
						 }
						
					})

		}
		}
	 $scope.dynamicsearch = function(primarySkills,jobCategory,jobLocation,experience){
		 $scope.skil.skills="";$scope.job.jobCategory1="";$scope.exp2.totalExperience="";$scope.location2.city="";$scope.customer1.customer="";$scope.budget1.budget="";
		 if(primarySkills==""){
			 primarySkills=undefined;
		 }
		 if(jobCategory==""){
			 jobCategory=undefined;
		 }
		 if(jobLocation==""){
			 jobLocation=undefined;
		 }
		 if(experience==""){
			 experience=undefined;
		 }
		 if(primarySkills==undefined&&jobCategory==undefined&&jobLocation==undefined&&experience==undefined){
			 $scope.postareq(); 
		 }
		 else{
			 RAService.searchRequirementCustomer(primarySkills,jobCategory,jobLocation,experience,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
				
				 $scope.errormsg=data.errorCode;
				 if($scope.errormsg=="NO_CONTENT"){
					 console.log("no data found");
					 $scope.divshowing=true;
					 $scope.divshowing1=false;
				 }
				 if($scope.errormsg=="OK"){
				   $scope.list = data.result;
				   console.log($scope.list);
				   $scope.totalCount= data.count;
				   $scope.divshowing1=true;
				   $scope.divshowing=false;				   
					$scope.divLoadPagination=false;
					$scope.divSearchPagination=true;
					$scope.divFilterPagination=false;
				 }
				
			   })			
		}
	 }
	 
	 $scope.search1 = function(search1){
			
			RAService.searchrequirement(search1.skills,search1.jobCategory,search1.city,search1.totalExperience).then(function(data){
				
				$scope.list = data.result;
				console.log($scope.list);
			})
		}
	 
	 $scope.clearFunction = function(){
	
		 if($scope.primarySkills=="" && $scope.jobCategory=="" && $scope.jobLocation=="" && $scope.experience==""){
			 $scope.postareq(); 
		 }
		}
	 //getDetails
	 $scope.getDetails=function(reqData){
		         $scope.budget=reqData.budget;
		    	 $scope.businessStatus=reqData.businessStatus;
		    	 $scope.certifications= reqData.certifications;
		    	 $scope.companyName=reqData.companyName;
		    	 $scope.description=reqData.description;
		    	 $scope.gst=reqData.gst;
		    	 $scope.isSowUser=reqData.isSowUser;
		    	 $scope.job=reqData.job;
		    	 $scope.jobCategory=reqData.jobCategory;
		    	 $scope.jobId=reqData.jobId;
		    	 $scope.jobLocation=reqData.jobLocation;
		    	 $scope.jobRole=reqData.jobRole;
		         $scope.jobTitle=reqData.jobTitle;
		    	 $scope.jobType=reqData.jobType;
		    	 $scope.joinWithin=reqData.joinWithin;
		      	 $scope.monthsOfExperience=reqData.monthsOfExperience;
		    	 $scope. phoneNumber=reqData.phoneNumber;
		    	 $scope.postedDate=reqData.postedDate;
		    	 $scope.primarySkills=reqData.primarySkills;
		    	 $scope.qualifications=reqData.qualifications;
		    	 $scope.rateType=reqData.rateType;
		    	 $scope.registrationId=reqData.registrationId;
		    	 $scope.resources=reqData.resources;
		    	 $scope.secondarySkills=reqData.secondarySkills;
		    	 $scope.status=reqData.status;
		    	 $scope.uploadFile=reqData.uploadFile;
		    	 $scope.yearsOfExperience=reqData.yearsOfExperience;
		 
	 }
	 
}])