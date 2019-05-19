
		resourceApp.controller('vendorpraposedresourceCtrl',["$scope","$rootScope","$state","$http","$stateParams","RAService",function($scope,$rootScope, $state,$http, $stateParams, RAService){
		//	$scope.$on('$viewContentLoaded', function () {
		//		$scope.getresourcelist();
	//	        $scope.sidelist();
				
		//	})
			
			
	angular.element(document).ready(function () {
		$scope.getresourcelist();
		$scope.sidelist();
	});
			
			
			$scope.skills = [ "java", "jsp", "servlets", "Spring",
					"Html", "Css", "Bootstrap", "Angularjs", "Nodejs",
					"Php", "Phyton", "MySQL", "MongoDB", "Oracle",
					"Sql Server" ];
			$scope.allListsfunc= function(){
			
		 		RAService.resourcelistaccordion().then(function(data){
		 	        $scope.skillslist = data;
		 	     
		 	       
		 	    });
		 		}
			
			$scope.sidelist=function() {
				RAService.allresLists().then(
						function(data) {
							$scope.skillslist = data;

						});
			};
		 		
		 		
			$scope.requirementsearch1 = function(skills,jobCategory,currentLocation,experience) {

			
				$rootScope.experience=experience;
		 		$rootScope.skills=skills;
		 		$rootScope.jobCategory=jobCategory;
		 		$rootScope.currentLocation=currentLocation;
				$state.go('vendor.requirementsearach');
			}
			$scope.dynamicsearch = function(primarySkills,jobCategory,jobLocation,experience){
				
				 RAService.searchresource(primarySkills,jobCategory,jobLocation,experience).then(function(data){
					   $scope.resourcelist = data.result;


				   })			
			}
			
			//$scope.lst = [];
			$scope.search1 = { skills: [] };
			  $scope.job = { jobCategory: [] };
			  $scope.exp = {totalExperience:[]};
			  $scope.location1 = {city:[]};
			  $scope.customer1 = {customer:[]};
			  $scope.budget1 = {budget:[]};
			$scope.search2 = function(){	
			
					  if($scope.search1.skills.length == 0){
						  $scope.search1.skills = "undefined";
					  }
					  if($scope.job.jobCategory.length == 0){
						  $scope.job.jobCategory = "undefined";
					  }
					  if($scope.exp.totalExperience.length == 0){
						  $scope.exp.totalExperience = "undefined";
					  }
					  if($scope.location1.city.length == 0){
						  $scope.location1.city = "undefined";
					  }
					  if($scope.customer1.customer.length == 0){
						  $scope.customer1.customer = "undefined";
					  }
					  if($scope.budget1.budget.length == 0){
						  $scope.budget1.budget = "undefined";
					  }
				
		      RAService.searchsidefilterresource($scope.search1.skills,$scope.job.jobCategory,$scope.location1.city,$scope.exp.totalExperience,$scope.customer1.customer,$scope.budget1.budget).then(function(data){
			    			$scope.resourcelist = data.result;


			    		})
			    	 
			}
			

		/*    //pagination
			
		    $scope.maxSize = 2;     // Limit number for pagination display number.  
		    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
		    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
		    $scope.pageSizeSelected = 3; // Maximum number of items per page.
		    
			$scope.getresourcelist = function(){
				$scope.local = localStorage.getItem('registrationId');	
				
				RAService.vendoraddresourcelist($scope.local,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
					debugger;
					
					$scope.resourcelist = data.result;
					console.log($scope.resourcelist);
					 $scope.totalCount = data.count;
		            // console.log($scope.totalCount);
		           
		     },function (err) {  
		    	 $scope.errorMessage = err;
		     });
		 }
			  $scope.pageChanged = function(){
				  debugger;
		          $scope.getresourcelist()
		              console.log('Page changed to: ' + $scope.pageIndex);
		      };*/
		    
			//pagination
			  $scope.maxSize = 2;     // Limit number for pagination display number.  
			    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
			    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
			    $scope.pageSizeSelected = 10; // Maximum number of items per page.
			   
			
			$scope.getresourcelist = function(){


				$scope.local = localStorage.getItem('registrationId');
				RAService.vendorpraposedresource($scope.local).then(function(data) {
			        $scope.resourcelist = data.result;


			        $scope.totalCount = data.count;
				},function(err){
					if(err){
						$scope.errorMessage = err;
					}
				})
			}
			$scope.pageChanged = function() {
		      $scope.getresourcelist()


		  };

			
		     
		    
			
			$scope.statusResource = function(resource){


				if(resource.status == "Active"){
					resource.status = "InActive";
				RAService.resourceStatus(resource).then(function(data){
					$scope.Resource = data.result;


				},function(err){
					if(err){
						$scope.errorMessage = err;
					}
				})
				} else {
					resource.status = "Active";
					RAService.resourceStatus(resource).then(function(data) {
						$scope.Resource = data.result;


					}, function(err) {
						if (err) {
							$scope.errorMessage = err;
						}
					})
				}
		}

		$scope.softlockResource = function(resource){


			if(resource.softLock == "Yes"){
				resource.softLock = "No";
			RAService.PostresourceSoft(resource).then(function(data){
			$scope.Presource = data.result;


			},function(err){
				if(err){
					$scope.errorMessage = err;
				}
			})
			} else {
				resource.softLock = "Yes";
				RAService.PostresourceSoft(resource).then(function(data) {
					$scope.Presource = data.result;


				}, function(err) {
					if (err) {
						$scope.errorMessage = err;
					}
				})
			}
			}


			$scope.hardlockResource = function(resource){


				if(resource.hardLock == "Yes"){
					resource.hardLock = "No";
				RAService.PostresourceHard(resource).then(function(data){
				$scope.Presource = data.result;


				},function(err){
					if(err){
						$scope.errorMessage = err;
					}
				})
				} else {
					resource.hardLock = "Yes";
					RAService.PostresourceHard(resource).then(function(data) {
						$scope.Presource = data.result;


					}, function(err) {
						if (err) {
							$scope.errorMessage = err;
						}
					})
				}
				}
			
			$scope.send_id=function(res_id){
				$scope.r_id=res_id;
				}


			$scope.uploadFile = function(resource_id,myFile){


			       var uploadFile = myFile;      						           

			        

			       var uploadUrl = "http://localhost:8081/ResourceAdda/rest/resource/uploadFile/"+resource_id;

			      /* var uploadUrl = "http://localhost:8081/ResourceAdda/rest/resource/uploadFile/"+resource_id;

			       var uploadUrl = "http://localhost:8081/ResourceAdda/rest/resource/uploadFile/"+resource_id;
		*/
			       RAService.uploadResumeToUrl(uploadFile,uploadUrl).then(function(data){
			       		$scope.f=data.result;




			       		alert('Uploaded Successfully')
			       },function(err){
			       if(err){
			           $scope.errorMessage = err;
			      	 }

			       })
			}

			
			$scope.send_id=function(res_id){
				$scope.r_id=res_id;
				}

			$scope.myBlobObject=undefined;
			$scope.filedownload=function(resourceId){


			    localStorage.setItem('count', 1);


			        RAService.Getfile(resourceId)
			        .then(function(data){//is important that the data was returned as Aray Buffer


			                //$scope.myBlobObject=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
			                $scope.myBlobObject=new Blob([data.result]);




			        },function(fail){


			                                    $scope.myBlobObject=[];
			                                });
			                            }
			



		} ]);