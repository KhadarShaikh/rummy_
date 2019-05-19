resourceApp.controller('vendorresourcelistCtrl',["$scope","$rootScope","$state","$http","$stateParams","RAService",function($scope,$rootScope, $state,$http, $stateParams, RAService){

      // $scope.$on('$viewContentLoaded', function () {
      //    $scope.getresourcelist();
        
      //       })
	angular.element(document).ready(function () {
		  $scope.getresourcelist();
	});
	
	
	
       
       $scope.skills = [ "Java", "jsp", "Servlets", "Spring",
                     "HTML", "CSS", "Bootstrap", "AngularJs", "Nodejs",
                     "Php", "Phyton", "MySQL", "MongoDB", "Oracle",
                     "SQL Server","angular5" ,"jquery","json"];
       $scope.Selectors = [ "skills", "totalExperience",
              "availability" ];
       $scope.SelectedCriteria = "";
       $scope.filterValue = "";    
       
       $scope.maxSize = 2;     // Limit number for pagination display number.  
       $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
       $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
       $scope.pageSizeSelected = 5; // Maximum number of items per page.
               
               
       $scope.requirementsearch1 = function(_id,registrationid,skills,jobCategory,currentLocation,experience) {

     
              $rootScope.resource_id=_id;
              
              $rootScope.resource_registrationid=registrationid;
              $rootScope.experience=experience;
               $rootScope.skills=skills;
               $rootScope.jobCategory=jobCategory;
               $rootScope.currentLocation=currentLocation;
              $state.go('vendor.requirementsearach');
       }
       
       $scope.getDetails=function(resource){
    	  
    	  
    	   $$hashKey: "object:85"
    		 
    		  $scope.budget=resource.budget;
  
    		   $scope.companyName=resource.companyName;
    		 
    			   $scope.country=resource.country;
    			   $scope.yearsOfExperience=resource.yearsOfExperience;
    		
    			   $scope.currentLocation=resource.currentLocation;
    		 
    		
    			   $scope.emailId=resource.emailId;
    		       $scope.firstName=resource.firstName
    		  
    			   $scope.gst=resource.gst;
    		  
    			   $scope.jobCategory=resource.jobCategory;
    		 
    		     $scope.lastName=resource.lastName
    		 
    			   $scope.mobileNumber=resource.mobileNumber;
    		 
    			   $scope.monthsOfExperience=resource.monthsOfExperience;
    		
    		     $scope.preferredLocation=resource.preferredLocation;
    		
    			   $scope.primarySkills=resource.primarySkills;
    		
    			   $scope.rate=resource.rate;
    		 
    			   $scope.secondarySkills=resource.secondarySkills;
    		
    			   $scope.state=resource.state;

    		   
       }
       
       
       $scope.dynamicsearch = function(primarySkills,jobCategory,jobLocation,experience){
            
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
             
            if( primarySkills ==undefined&&jobCategory == undefined&& jobLocation == undefined&&experience ==undefined){
	
            	$scope.getresourcelist();
}
            else{
            RAService.searchresourceforvendor(primarySkills,jobCategory,jobLocation,experience).then(function(data){
            	$scope.errormsg=data.errorCode;
            	 $scope.errorMessage=data.errorMessage;
            	 
            	 if($scope.errormsg=="NO_CONTENT"){
					 console.log("no data found");
					 $scope.divshowing_ven=false;
					 $scope.divshowing_ven1=true;
				 }
				 if($scope.errormsg=="OK"){
				   $scope.resourcelist = data.result;
				   console.log($scope.list);
				   $scope.divshowing_ven=true;
				   $scope.divshowing_ven1=false;
				   $scope.divLoadPagination=false;
					$scope.divSearchPagination=false;
					$scope.divFilterPagination=true;
				 }
				 $scope.search=null;
                   
                 })                     
       			}
       }
        $scope.search3 = function(search3){
                     
                     RAService.searchresource(search3.skills,search3.jobCategory,search3.city,search3.totalExperience).then(function(data){
                            
                            $scope.list = data.result;
                            console.log($scope.list);
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
                      
                       if( $scope.search1.skills == "undefined"&&  $scope.job.jobCategory == "undefined" &&  $scope.exp.totalExperience == "undefined" && $scope.location1.city == "undefined" && $scope.customer1.customer =="undefined"&& $scope.budget1.budget== "undefined"
                    	   ){
                    	    $scope.getresourcelist();
                       }
                       else{
                   
      RAService.searchsidefilterresource($scope.search1.skills,$scope.job.jobCategory,$scope.location1.city,$scope.exp.totalExperience,$scope.customer1.customer,$scope.budget1.budget).then(function(data){
                                
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
       							 /*$scope.skillslist= data.allList;*/
       							 console.log($scope.skillslist);
       						
       						 $scope.divshowing1=true;
  						   $scope.divshowing=false;
//  							$scope.divLoadPagination=false;
//  							$scope.divSearchPagination=false;
//  							$scope.divFilterPagination=true;
       							
       						 }
                         })
                       } 
                   
       }

       
           

       $scope.getresourcelist = function() {
    	   $scope.isLoading=true;
       //pagination
    	   $scope.divshowing_ven1=true;
   		$scope.divshowing_ven=false;
   		$scope.divLoadPagination=true;
   		$scope.divSearchPagination=false;
   		$scope.divFilterPagination=false;
       	//$scope.divshowing_ven1=true;
		//$scope.divshowing_ven=false;
       RAService.vendoraddresourcelist($scope.pageIndex,$scope.pageSizeSelected).then(function(data) {
    	
              $scope.resourcelist = data.result;
              $scope.skillslist = data.allList;
              $scope.totalCount = data.count;
              $scope.errorMessage=data.errorMessage;
              
              $scope.isLoading=false;
              $rootScope.vendor_resource_id=$scope.resourcelist[0]._id;
              localStorage.setItem('vendor_resource_id', $scope.resourcelist[0]._id);
              

              console.log($rootScope.vendor_resource_id);
              $rootScope.vendor_registrationId=$scope.resourcelist[0].registrationId;
            
              //$scope.sidefilterlist();
              console.log($scope.resourcelist);
            
              
       }, function(err) {
              if (err) {
                     $scope.errorMessage = err;
              }
       })
}
 
       /*
      $scope.sidefilterlist=function()
       {
    	
    	   RAService.getsidefilterslist().then(function(data) {
                  $scope.skillslist = data.result;
                 
                  console.log($scope.skillslist);
                
               
        }, function(err) {
               if (err) {
                      $scope.errorMessage = err;
               }
        })
 
    	   
       }
       */
//       $scope.getresourcelist();
        $scope.pageChanged = function() {
       $scope.getresourcelist()
           console.log('Page changed to: ' + $scope.pageIndex);
   };
     
  // $scope.pageChanged = function() {
 //      $scope.postareq()
 //          console.log('Page changed to: ' + $scope.pageIndex);
 //  };
   
   $scope.pageChangedSearchPagination = function() {
       $scope.dynamicsearch()
           console.log('Page changed to: ' + $scope.pageIndex);
   };
   
   $scope.pageChangedFilterPagination = function() {
       $scope.search1()
           console.log('Page changed to: ' + $scope.pageIndex);
   };
       
       $scope.statusResource = function(resource){
           
              if(resource.status == "Active"){
                     resource.status = "InActive";
              RAService.resourceStatus(resource).then(function(data){
                     $scope.Resource = data.result;
                     console.log($scope.Resource);
              },function(err){
                     if(err){
                            $scope.errorMessage = err;
                     }
              })
              } else {
                     resource.status = "Active";
                     RAService.resourceStatus(resource).then(function(data) {
                            $scope.Resource = data.result;
                            console.log($scope.Resource);
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
              console.log($scope.Presource);
       },function(err){
              if(err){
                     $scope.errorMessage = err;
              }
       })
       } else {
              resource.softLock = "Yes";
              RAService.PostresourceSoft(resource).then(function(data) {
                     $scope.Presource = data.result;
                     console.log($scope.Presource);
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
                     console.log($scope.Presource);
              },function(err){
                     if(err){
                            $scope.errorMessage = err;
                     }
              })
              } else {
                     resource.hardLock = "Yes";
                     RAService.PostresourceHard(resource).then(function(data) {
                            $scope.Presource = data.result;
                            console.log($scope.Presource);
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
                                                      
              

           
           
              var uploadUrl = "rest/resource/uploadFile/"+resource_id;

              RAService.uploadResumeToUrl(uploadFile,uploadUrl).then(function(data){
                     console.log(data.result);
                    // alert(data.result);
                   //  var file = angular.element('#file');
//                  if (data.result == ""){
//                         alert('Uploaded Successfully');
//                  }   


            
             //  $scope.isDisabled = true;
                       
                         $scope.f=data.result;
//                                   console.log($scope.f);
//                                   console.log("success");
                                   
                 
                            
              },function(err){
              if(err){
                  $scope.errorMessage = err;
                     }console.log("fail");
              })
       }

       
       $scope.send_id=function(res_id){
              $scope.r_id=res_id;
              }

       $scope.myBlobObject=undefined;
       $scope.filedownload=function(resourceId){
          
           localStorage.setItem('count', 1);
               console.log('download started, you can show a waiting animation');
               RAService.Getfile(resourceId)
               .then(function(data){//is important that the data was returned as Aray Buffer
                       console.log('Stream download complete, stop animation!');
                       $scope.myBlobObject=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                       $scope.myBlobObject=new Blob([data.result]);
                     
                       console.log($scope.myBlobObject.uploadResume);
               },function(fail){
                       console.log('Download Error, stop animation and show error message');
                                           $scope.myBlobObject=[];
                                       });
                                   }
       



} ]);
resourceApp.controller('vendoraddresourceCtrl',["$scope","$state","$stateParams","$filter","RAService","masterService",function($scope, $state, $stateParams, $filter,
		                                    RAService,masterService) {
	 // $scope.$on('$viewContentLoaded', function () {
	//	  $scope.masterdetails();	        
        //       })  
              
         angular.element(document).ready(function () {
        	  $scope.masterdetails();	
	});
	
               
	 
         var yearMS = 365 * (1000 * 60 * 60 * 24); // 365 days
         var now = new Date().getTime();
         var maxDobMS = now - (18 * yearMS);
         var minDobMS = now - (122.5 * yearMS);
         $scope.maxDobString = new Date(maxDobMS).toISOString().slice(0, 10);
         $scope.minDobString = new Date(minDobMS).toISOString().slice(0, 10);

         $scope.demoData = { dob: '' };
         
         $scope.$on('$viewContentLoaded', function() {
                                                        $scope.companyNameList = [];
                                                        $scope.companyId = [];
                                                        $scope.companyid();
                                                        $scope.resource = {};
                                                 })
                                           $scope.stage = "";
                                       // Navigation functions
                                       $scope.next = function (stage) {
                                         $scope.direction = 1;
                                         $scope.stage = stage;
                                   
                                        /* if ($scope.frm.$valid) {
                                           $scope.direction = 1;
                                           $scope.stage = stage;
                                         
                                         }*/
                                       };
                                       
                                       // master API for job Category
                                       $scope.masterdetails=function(){
                                            
                                              RAService.getmasterdetails().then(function(data){                                              
                                                     $scope.jobcategorylist_vendor=data.result.jobCategory;
                                                     console.log($scope.jobcategorylist_vendor);
                                                          $scope.jobtypelist_vendor=data.result.jobType;
                                                          $scope.primarySkills_vendor=data.result.primarySkills;
                                                          $scope.secondarySkills_vendor=data.result.secondarySkills;
                                                          $scope.noticelist=data.result.joinWithIn;
                                              })                  
                                          
                                              }
                                     
                                       $scope.back = function (stage) {
                                         $scope.direction = 0;
                                         $scope.stage = stage;
                                       };
                                       
                                                 $scope.companyid = function() {
                                                        RAService.getCompanyList().then(function(data) {
                                                               debugger;
                                                               $scope.list = data.result;
                                                               console.log($scope.list[0].companyName);
                                                               for (var i = 0; i < $scope.list.length; i++) {
                                                                      $scope.companyNameList.push($scope.list[i].companyName);
                                                                      $scope.companyId.push($scope.list[i]._id);
                                                               }
                                                               $scope.companyid = function() {
                                                                      for (var j = 0; j < $scope.companyNameList.length; j++) {
                                                                             if ($scope.companyName1 == $scope.companyNameList[j]) {
                                                                                           $scope.comId = $scope.companyId[j];
                                                                                                  console.log($scope.comId);

                                                                                                         }
                                                                                                  }
                                                                                           }

                                                                                    });
                                                 }
                                                 $scope.gender = [ "Male", "Female", "Transgender" ];
                                                 $scope.experience = [ "1-2", "2-4","4-6", "6-8", "8-10","10+ more..." ];
                                                 $scope.currentLocation = [ "Bangalore","Chennai","Hyderabad","Pune","Itanagar","Dispur","Patna","Raipur","Panaji","Gandhinagar","Punjab","Shimla","Srinagar","Ranchi",
                                                     "Thiruvananthapuram","Bhopal","Mumbai","Imphal","Shillong","Aizawl","Kohima","Bhubaneswar","Jaipur","Gangtok","Noida","Amaravathi","Agartala","Lucknow","Dehradun","Kolkata" ];
                                                                                           
                                                                                           $scope.preferredLocation = [ "Bangalore","Chennai","Hyderabad","Pune","Itanagar","Dispur","Patna","Raipur","Panaji","Gandhinagar","Punjab","Shimla","Srinagar","Ranchi",
                                                                                       "Thiruvananthapuram","Bhopal","Mumbai","Imphal","Shillong","Aizawl","Kohima","Bhubaneswar","Jaipur","Gangtok","Noida","Amaravathi","Agartala","Lucknow","Dehradun","Kolkata" ];
                                                 
                                                                                           $scope.States=["Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Goa","Gujarat","Haryana",
                                                                                                  "Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Lakshadweep","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland",
                                                                                                  "Orissa","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttaranchal","Uttar Pradesh","West Bengal"];
                                                                                           
                                                                                           $scope.Countries = ["Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", 
                                                                                                  "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil",
                                                                                                  "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", 
                                                                                                  "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", 
                                                                                                  "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
                                                                                                  "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
                                                                                                  "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia",
                                                                                                  "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", 
                                                                                                  "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia",
                                                                                                  "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", 
                                                                                                  "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands",
                                                                                                  "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
                                                                                                  "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", 
                                                                                                  "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga",
                                                                                                  "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
                                                                                                  "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela",
                                                                                                  "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"]
                                                                                           
                                                 $scope.Rate=["Hourly","Per-Day","Per-Week","Per-Month"];
                                                 $scope.relocate=["Yes","No"];
                                                  $scope.yearsOfExperience = ["0 Year","1 Year", "2 Years", "3 Years","4 Years", "5 Years", "6 Years","7 Years", "8 Years", "9 Years","10 Years","11 Years"];
                                            $scope.monthsOfExperience = ["0 Month","1 Month", "2 Months", "3 Months","4 Months", "5 Months", "6 Months","7 Months", "8 Months", "9 Months","10 Months","11 Months"];
                                                 
                                            $scope.IsVisible = false;
                                                $scope.IsVisible = false;
                                        $scope.ShowPassport = function (value) {
                                            //If DIV is visible it will be hidden and vice versa.
                                            if(value == 'Y'){
												$scope.IsVisible = true;
											} else{
											   $scope.IsVisible = false;	
											}
                                               //$scope.IsVisible = value == "N";
                                        }
                                            
                                                 $scope.addresource = function(resource) {
                                                       
                                                        //$scope.resource.registrationId = $scope.comId;
                                                        $scope.resource.dateOfBirth = $filter('date')(
                                                        $scope.resource.dateOfBirth,"MM/dd/yyyy");
                                                        $scope.resource.primarySkills=$scope.resource.primarySkills.toString();
                                                  $scope.resource.secondarySkills=$scope.resource.secondarySkills.toString();  
												  
                                                 // $scope.resource.preferredLocation=$scope.resource.preferredLocation.toString();
                                                
                                                  if($scope.resource.relocate=="Yes")
                                                  {
                                                         $scope.resource.preferredLocation=$scope.resource.preferredLocation.toString();
														 
                                                  }
                                                  else{
                                                         
                                                  $scope.resource.preferredLocation=$scope.resource.preferredLocation;
                                                         
                                                  }
                                                         $scope.resource.registrationId = localStorage.getItem('registrationId');
                                                       
                                                         console.log($scope.registrationId);
                                                        RAService.addresource($scope.resource).then(
                                                                      function(data) {
                                                                             $scope.resourceadd = data.result;
                                                                             console.log($scope.resourceadd);
                                                                             $state.go('vendor.resourcelist');
                                                                      }, function(err) {
                                                                             if (err) {
                                                                                    $scope.errorMessage = err;
                                                                             }
                                                                      })
                                                 }

                                          } ]);
resourceApp.controller('vendorupdateresourceCtrl',["$scope","$state","$stateParams","$filter","RAService","masterService",function($scope, $state, $stateParams, $filter,RAService,masterService) {
                                              //   $scope.$on('$viewContentLoaded', function() {
                                                //        $scope.resource = {};                                                 
                                               //         $scope.getResourceById();
                                                       
                                                    
                                              //   })
	angular.element(document).ready(function () {
		 $scope.resource = {};                   
		 $scope.masterdetails();
       
	});
                                                 
                                                 
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
                                     

                                                 $scope.gender = [ "Male", "Female", "Transgender" ];
                                                 //$scope.relocate=["Yes","No"];
                                                 $scope.yearsOfExperience = ["0 Year","1 Year", "2 Years", "3 Years","4 Years", "5 Years", "6 Years","7 Years", "8 Years", "9 Years","10 Years","11 Years"];
                                                 $scope.monthsOfExperience = ["0 Month","1 Month", "2 Months", "3 Months","4 Months", "5 Months", "6 Months","7 Months", "8 Months", "9 Months","10 Months","11 Months"];
                                                 $scope.experience = [ "1-2 years", "2-4 years","4-6 years", "6-8 years", "8-10 years","10+ more..." ];
                                                 $scope.currentLocation = [ "Bangalore","Chennai","Hyderabad","Pune","Itanagar","Dispur","Patna","Raipur","Panaji","Gandhinagar","Punjab","Shimla","Srinagar","Ranchi",
                                                     "Thiruvananthapuram","Bhopal","Mumbai","Imphal","Shillong","Aizawl","Kohima","Bhubaneswar","Jaipur","Gangtok","Noida","Amaravathi","Agartala","Lucknow","Dehradun","Kolkata" ];
                                                                                           
                                                 $scope.preferredLocation = [ "Bangalore","Chennai","Hyderabad","Pune","Itanagar","Dispur","Patna","Raipur","Panaji","Gandhinagar","Punjab","Shimla","Srinagar","Ranchi",
                                                                                       "Thiruvananthapuram","Bhopal","Mumbai","Imphal","Shillong","Aizawl","Kohima","Bhubaneswar","Jaipur","Gangtok","Noida","Amaravathi","Agartala","Lucknow","Dehradun","Kolkata" ];
                                                 
                                                 $scope.States=["Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Goa","Gujarat","Haryana",
                                                                                                  "Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Lakshadweep","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland",
                                                                                                  "Orissa","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttaranchal","Uttar Pradesh","West Bengal"];
                                                                                           
                                                 $scope.Countries = ["Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", 
                                                                                                  "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil",
                                                                                                  "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", 
                                                                                                  "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", 
                                                                                                  "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
                                                                                                  "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
                                                                                                  "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia",
                                                                                                  "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", 
                                                                                                  "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia",
                                                                                                  "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", 
                                                                                                  "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands",
                                                                                                  "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
                                                                                                  "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", 
                                                                                                  "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga",
                                                                                                  "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
                                                                                                  "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela",
                                                                                                  "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"]
                                                                                           
                                                  $scope.Rate=["Hourly","Per-Day","Per-Week","Per-Month"];
                                                 
                                                 $scope.IsVisible = false;
                                                 $scope.ShowPassport = function (value) {
	                                            //If DIV is visible it will be hidden and vice versa.
	                                            if(value == 'Y'){
													$scope.IsVisible = true;
												} else{
												   $scope.IsVisible = false;	
												}
	                                               //$scope.IsVisible = value == "N";
	                                        }  
                                                 
                                           $scope.getResourceById = function(){
                                                    
                                                      $scope.resource.registrationId = $scope.comId;
                                                         RAService.resourcegetById($stateParams.resourceId).then(function(data){
                                                                             $scope.resource = data.result;
                                                                             $scope.resource.dateOfBirth = new Date($scope.resource.dateOfBirth);
                                                                             console.log($scope.resource.primarySkills);
                                                                             console.log($scope.resource);
                                                                             $scope.resource.primarySkills = $scope.resource.primarySkills.split(',');
                                                                             $scope.resource.secondarySkills = $scope.resource.secondarySkills.split(',');
                                                                              $scope.resource.preferredLocation=$scope.resource.preferredLocation.split(',');
                                                                             console.log($scope.resource.primarySkills);
                                                                             console.log($scope.resource.secondarySkills);
                                                                             if($scope.resource.relocate=="No"){
                                                                                    $scope.IsVisible = true;
                                                                             }
                                                                            // $scope.masterdetails();
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
                                             
                                               RAService.getmasterdetails().then(function(data){                                              
                                                      $scope.jobcategorylist_vendor=data.result.jobCategory;
                                                      console.log($scope.jobcategorylist_vendor);
                                                           $scope.jobtypelist_vendor=data.result.jobType;
                                                           $scope.primarySkills_vendor=data.result.primarySkills;
                                                           $scope.secondarySkills_vendor=data.result.secondarySkills;
                                                           $scope.noticelist=data.result.joinWithIn;
                                                           $scope.getResourceById();
                                               })                  
                                           
                                               }
                                                 $scope.updateResource = function() {
                                                      
                                                        
                                                         $scope.resource.preferredLocation=$scope.resource.preferredLocation.toString();
                                                         $scope.resource.primarySkills = $scope.resource.primarySkills.toString();
                                                               $scope.resource.secondarySkills = $scope.resource.secondarySkills.toString();
                                                        $scope.resource.dateOfBirth = $filter('date')($scope.resource.dateOfBirth,"MM/dd/yyyy");
                                                        $scope.resource.registrationId = localStorage.getItem('registrationId');
                                                        RAService.updateresource($scope.resource).then(function(data) {
                                                                             $scope.updateresource = data.result;
                                                                             console.log($scope.updateresource);
                                                                             $state.go('vendor.resourcelist');
                                                                      }, function(err) {
                                                                             if (err) {
                                                                                    $scope.errorMessage = err;
                                                                             }
                                                                      })
                                                 }
                                                 

                                          } ]);