resourceApp.controller('vendorreqlistCtrl',	['$scope','RAService','$rootScope','$state',function($scope, RAService,$rootScope, $state) {
					
				/*	$scope.$on('$viewContentLoaded', function() {
						//$scope.allListsfunc();
						$scope.postareq();
						$scope.sidefilters_allrequirement();
					})*/
					angular.element(document).ready(function () {
						//$scope.allListsfunc();
						$scope.postareq();
					//	$scope.sidefilters_allrequirement();							
							//$scope.sidefilterlist();
					 });
				
			
						/*
						    $scope.allListsfunc= function(){
						$scope.divshowing_ven1=true;
						$scope.divshowing_ven=false; 
						$scope.divLoadPagination=true;
						$scope.divSearchPagination=false;
						$scope.divFilterPagination=false;
						debugger;
						RAService.allreqLists().then(function(data){
						$scope.skillslist = data;						
						console.log($scope.allListsfunc);

						});
					}	
					*/				  
						$scope.dynamicsearch = function(primarySkills,jobCategory,jobLocation,experience){
							debugger;							 
							if(primarySkills==""){
				            	 primarySkills = undefined;
				           }
				              if(jobCategory==""){
				            	jobCategory = undefined;
				           }
				              if(jobLocation==""){
				            	 jobLocation = undefined;
				           }
				             
				              if(experience==""){
				            	  experience = undefined;
				           }
				              if(primarySkills==undefined&&jobCategory==undefined&&jobLocation==undefined&&experience==undefined){
				            	  $scope.postareq();
				              }
				              else{
							 RAService.searchrequirementvendor(primarySkills,jobCategory,jobLocation,experience).then(function(data){
								   $scope.errormsg=data.errorCode;
					            	 if($scope.errormsg=="NO_CONTENT"){
										 console.log("no data found");
										 $scope.divshowing_ven=true;
										 $scope.divshowing_ven1=false;
										 
									 }
									 if($scope.errormsg=="OK"){
									   $scope.list = data.result;
									   console.log($scope.list);
									   $scope.divshowing_ven=false;
									   $scope.divshowing_ven1=true;
									   $scope.divLoadPagination=false;
										$scope.divSearchPagination=true;
										$scope.divFilterPagination=false;
									 }
									 $scope.search=null;
							   })			
						}
						}
					
						$scope.skills = [ "Java", "jsp", "Servlets", "Spring",
							"HTML", "CSS", "Bootstrap", "AngularJs", "Nodejs",
							"Php", "Phyton", "MySQL", "MongoDB", "Oracle",
							"SQL Server" ];
						
						
						//pagination
						  $scope.maxSize = 2;     // Limit number for pagination display number.  
						    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
						    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
						    $scope.pageSizeSelected = 5; // Maximum number of items per page.
						    
						  
						
						$scope.postareq = function() {
				  $scope.isLoading=true;
					 	$scope.divshowing_ven1=true;
						$scope.divshowing_ven=false;
						$scope.divLoadPagination=true;
						$scope.divSearchPagination=false;
						$scope.divFilterPagination=false;
						
						
						RAService.venpostareqList($scope.pageIndex,$scope.pageSizeSelected).then(function(data) {
							$scope.list = data.result;
							 $scope.skillslist = data.allList;
							 $scope.isLoading=false;
						//	$scope.sidefilters_allrequirement(); //sidefilters API Function
							console.log($scope.list);
							 $scope.totalCount = data.count;
						
						}, function(err) {
							if (err) {
								$scope.errorMessage = err;
							}
						})
					}
					
						
						$scope.getDetails=function(data){     
							
							$scope.jobCategory=data.jobCategory;
							
                                    $scope.resources=data.resources;
									$scope.budget=data.budget;
							
								
									$scope.companyName=data.companyName;
							
									$scope.description=data.description;
							
									$scope.gst=data.gst;
							
							      $scope.jobType=data.jobType;
							
									$scope.jobCategory=data.jobCategory;
							
									$scope.jobId=data.jobId;
								
									$scope.jobLocation=data.jobLocation;
								
									$scope.joinWithin=data.joinWithin;
								
									$scope.monthsOfExperience=data.monthsOfExperience;
							
									$scope.phoneNumber=data.phoneNumber;
								
									$scope.primarySkills=data.primarySkills;
								
									$scope.qualifications=data.qualifications;
							
									$scope.rateType=data.rateType;
								
								
									$scope.secondarySkills=data.secondarySkills;
								
							
									$scope.yearsOfExperience=data.yearsOfExperience;
								
								
						}
						
						
						
//						 $scope.pageChanged = function() {
//			                 $scope.postareq()
//			                     console.log('Page changed to: ' + $scope.pageIndex);
//			             };
					
			      	   $scope.skil1 = {
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
			      				
			      				if ($scope.skil1.skills.length == 0) {
			      					$scope.skil1.skills = "undefined";
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
			      				if($scope.skil1.skills == "undefined"&&$scope.job.jobCategory1 == "undefined"&&$scope.exp2.totalExperience == "undefined"&&
			      					$scope.location2.city == "undefined"&&$scope.customer1.customer == "undefined"&&$scope.budget1.budget == "undefined"){
			      					$scope.postareq();
                                   }
			      				else{
			      				debugger;
			      				RAService.searchsidefilterrequirmentvendor(
			      						$scope.skil1.skills,
			      						$scope.job.jobCategory1,
			      						$scope.location2.city,
			      						$scope.exp2.totalExperience,
			      						$scope.customer1.customer,
			      						$scope.budget1.budget).then(
			      						function(data) {
			      							$scope.list = data.result;
			      							console.log($scope.list);
			      							 $scope.errormsg=data.errorCode;
			      							if($scope.errormsg=="NO_CONTENT"){
												 console.log("no data found");
												/* $scope.skillslist= data.allList;*/
												 $scope.divshowing_cus=true;
												 $scope.divshowing_cus1=false;
											 }
											 if($scope.errormsg=="OK"){
												 $scope.list = data.result;
					      							console.log($scope.list);
													$scope.totalCount = data.count;
													$scope.divshowing_ven=false;
													 $scope.divshowing_ven1=true;
													$scope.divLoadPagination=false;
													$scope.divSearchPagination=false;
													$scope.divFilterPagination=true;
											 }
			      						})

			      			}
			      			}
			      			
			      			/*
			      			$scope.sidefilters_allrequirement= function()
							{
			      				debugger;
						    	   RAService.getsidefilters_allrequirements().then(function(data) {
						                  $scope.skillslist = data.result;
						                  debugger;
						                  console.log($scope.skillslist);
						                
						               
						        }, function(err) {
						               if (err) {
						                      $scope.errorMessage = err;
						               }
						        })
								
								
							}
*/
			      			$scope.pageChanged = function() {
								$scope.postareq()
								console.log('Page changed to: '
										+ $scope.pageIndex);
							};
							
							 $scope.pageChangedSearchPagination = function() {
							        $scope.dynamicsearch()
							            console.log('Page changed to: ' + $scope.pageIndex);
							    };
							    
							    $scope.pageChangedFilterPagination = function() {
							        $scope.checkbox()
							            console.log('Page changed to: ' + $scope.pageIndex);
							    };	
			      			
			                
					$scope.resourcesearch = function(_id,skills,jobCategory,jobLocation,totalExperience) {	
						debugger;
						$rootScope.totalExperience=totalExperience;
				 		$rootScope.skills=skills;
				 		$rootScope.jobCategory=jobCategory;
				 		$rootScope.jobLocation=jobLocation;
				 		$rootScope.requirement_id=_id;
				 		$rootScope._id=localStorage.getItem('registrationId');
				 		
//						localStorage.setItem('requirementId', _id);
//						localStorage.setItem('skills', skills);
//						localStorage.setItem('jobCategory', jobCategory);
//						localStorage.setItem('jobLocation', jobLocation);
//						localStorage.setItem('totalExperience', totalExperience);
						$state.go('vendor.resourcecategory');
					}
					$scope.search1 = function(search1){
						debugger;
						RAService.searchrequirement(search1.skills,search1.jobCategory,search1.city,search1.totalExperience).then(function(data){
							
							$scope.list = data.result;
							console.log($scope.list);
						})
					}

					$scope.requirement = function(postrequirement) {

						if (postrequirement.status == "Active") {
							postrequirement.status = "Inactive";
							RAService.requirementStatus(postrequirement).then(
									function(data) {
										$scope.aaaa = data.result;
										console.log($scope.aaaa);
									}, function(err) {
										if (err) {
											$scope.errorMessage = err;
										}
									})
						} else {
							postrequirement.status = "Active";
							RAService.requirementStatus(postrequirement).then(
									function(data) {
										$scope.aaaa = data.result;
										console.log($scope.aaaa);
										$scope.totalCount = data.count;
									}, function(err) {
										if (err) {
											$scope.errorMessage = err;
										}
									})
						}
					}

				} ])
				
resourceApp.controller('vendorreqsummaryCtrl',["$scope","$rootScope","$state","$stateParams","RAService",function($scope,$rootScope,$state,$stateParams,RAService){
    $scope.$on('$viewContentLoaded', function () {
		$scope.postrequirement = {};
       
		 $scope.edit();
        })
		
   
	
		
        $scope.jobcategory = ["Java Developer","UI Developer","IDM Consultant",".Net Developer"];
	    $scope.jobtype = ["Contract","Full-time","Part-time"];
	    $scope.jobRole = ["Fresher","Intern","Trainee","Junior Developer","Senior Developer","Project Lead"];
	    $scope.joblocation= ["Bangalore","Chennai","Hyderabad","Pune","Itanagar","Dispur","Patna","Raipur","Panaji","Gandhinagar","Punjab","Shimla","Srinagar","Ranchi",
        	"Thiruvananthapuram","Bhopal","Mumbai","Imphal","Shillong","Aizawl","Kohima","Bhubaneswar","Jaipur","Gangtok","Noida","Amaravathi","Agartala","Lucknow","Dehradun","Kolkata"];
        $scope.experience = ["1-2 years","2-3 years","3-5 years","5-7 years","7-10 years"];
		$scope.primaryskills = ["Java","JDBC","HTML5","CSS3","Javascript","AngularJS"];
		$scope.Rate=["Hourly","Per-Day","Per-Week","Per-Month",];
		$scope.skills=["Java","jsp","servlets","Spring","HTML","CSS","Bootstrap","AngularJs","Nodejs","Php","Phyton","MySQL","MongoDB","Oracle","SQL Server"];
		$scope.secondaryskills = ["Oracle","MYSQL","SQL Server","MongoDB","WebRTC","Web Socket"];
        $scope.joining = ["Immediate","10-15 days","15-30 days","30-45 days"];
       
        $scope.edit = function(){
        	RAService.postareqGetById($stateParams.postId).then(function(data){
					$scope.postrequirement = data.result;
					console.log($scope.postrequirement.primarySkills);
					$scope.skills_primary=$scope.postrequirement.primarySkills;
					$scope.skills_secondary=$scope.postrequirement.secondarySkills;
					console.log($scope.postrequirement);
					$scope.primary_skill=$scope.postrequirement.primarySkills;
					$scope.secondary_skill= $scope.postrequirement.secondarySkills;
					$scope.postrequirement.primarySkills = $scope.postrequirement.primarySkills.split(',');
					$scope.postrequirement.secondarySkills = $scope.postrequirement.secondarySkills.split(',');
					console.log($scope.postrequirement.primarySkills);
					console.log($scope.postrequirement.secondarySkills);
					
					}),
					function(err){
						if(err){
							$scope.errorMessage = err;
						}else{
							$scope.errorMessage = err;
					   }   
					}
}

  


}]);

