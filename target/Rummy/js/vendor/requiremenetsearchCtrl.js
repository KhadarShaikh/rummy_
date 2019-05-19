
resourceApp.controller('requirementsearchCtrl',['$scope','$rootScope','RAService','$state',function($scope,$rootScope,RAService,$state){

	$scope.maxSize = 2;     // Limit number for pagination display number.  
    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
    $scope.pageSizeSelected = 5; // Maximum number of items per page.
       var totalExperience=$rootScope.experience;
       var skills=$rootScope.skills;
       var jobCategory=$rootScope.jobCategory;
       var currentLocation=$rootScope.currentLocation;
       var resource_id=$rootScope.resource_id;
   
               RAService.searchrequirement(skills,jobCategory,currentLocation,totalExperience,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
                      $scope.requirement = data.result;
                      $scope.errorMessage=data.errorMessage;
                     // $scope.skillslist=data.allList;
                      $scope.sidefilterlist();
               
               }) 
               
               $scope.sidefilterlist=function()
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
               //$scope.lst = [];
               $scope.search1 = { skills: [] };
                 $scope.job = { jobCategory: [] };
                 $scope.exp = {totalExperience:[]};
                 $scope.location1 = {city:[]};
                 $scope.customer1 = {customer:[]};
                 $scope.budget1 = {budget:[]};
                 $scope.vendor1 = {vendors :[]};
                 
               
               $scope.allListsfunc = function(){       
                   debugger;
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
                            if($scope.vendor1.vendors.length == 0){
                                $scope.vendor1.vendors = "undefined";
                         }
                            if( $scope.search1.skills == "undefined"&&  $scope.job.jobCategory == "undefined" &&  $scope.exp.totalExperience == "undefined" && $scope.location1.city == "undefined" && $scope.customer1.customer =="undefined"&& $scope.budget1.budget== "undefined"
                         	   && $scope.vendor1.vendors == "undefined"){
                         	    //$scope.getresourcelist();
                            }
                            else{
                           debugger;
                           RAService.searchsidefilterresource($scope.search1.skills,$scope.job.jobCategory,$scope.location1.city,$scope.exp.totalExperience,$scope.customer1.customer,$scope.budget1.budget,$scope.vendor1.vendors).then(function(data){
                                     $scope.resourcelist = data.result;
                                     console.log($scope.resourcelist);
                              })
                            } 
                        
            }
                

               
                $scope.praposeResource = function(_id,registrationId){ 
                       debugger;
                      var resource_id=$rootScope.resource_id;
                      var resource_registrationid=$rootScope.resource_registrationid;
                      var requirementId =_id;
                      var customer_registrationId =registrationId;
                      //var vendor_registrationId = localStorage.getItem('registrationId');
                      debugger;
                      RAService.praposeResourceCustomer(resource_id,resource_registrationid,requirementId,customer_registrationId).then(function(data){
                                    $scope.list = data.result;
                                    console.log($scope.list);
                                    alert("success");
                             })       
                      }
               
               $scope.skills = [ "java", "jsp", "servlets", "Spring",
                     "Html", "Css", "Bootstrap", "Angularjs", "Nodejs",
                     "Php", "Phyton", "MySQL", "MongoDB", "Oracle",
                     "Sql Server" ];
               /*$scope.allListsfunc= function(){
                     debugger;
                     RAService.allreqLists().then(function(data){
                     $scope.skillslist = data.primarySkillset;
                     console.log($scope.allListsfunc);

                     });
                     }
                     $scope.companyfunc= function(){
                     RAService.allreqLists().then(function(data){
                     $scope.company = data.vendorSet;

                     });
                     }
                     $scope.budgetfunc= function(){
                     RAService.allreqLists().then(function(data){
                     $scope.budget = data.budgetSet;

                     });
                     }
                     $scope.experiencefunc=function(){
                     RAService.allreqLists().then(function(data){
                     $scope.experience =data.yearsOfExperiencSet;
                     console.log($scope.experience)
                     })
                     }
                     $scope.locationfunc = function(){
                     RAService.allreqLists().then(function(data){
                     $scope.location = data.currentLocationSet;
                     })
                     }
                     $scope.jobcategoryfunc = function(){
                     RAService.allreqLists().then(function(data){
                     $scope.Jobc = data.jobCategorySet;
                     })
                     }*/
}]);

