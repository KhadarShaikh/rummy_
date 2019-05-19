resourceApp.controller('customerresourcesearchCtrl',['$scope','RAService','$rootScope','$state',function($scope,RAService,$rootScope,$state){
	  	 debugger;
	  	 
	 	$scope.$on('$viewContentLoaded', function() {
			$scope.getresourcelist();								
			$scope.sidefilterlist();
			
		})
	 	
	 	//pagination
	 	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	 	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	 	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	 	    $scope.pageSizeSelected = 5; // Maximum number of items per page.	
	 	    
	    var primarySkills9 =   $rootScope.primarySkills1;
	    var jobCategory9 = 	$rootScope.jobCategory;
	    var jobLocation9 = 	$rootScope.jobLocation;
	    var yearsOfExperience9 = 	$rootScope.experience;
	    var Requirement_Id=$rootScope._id;
	 	
	    $scope.getresourcelist ();
	 		$scope.getresourcelist = function() {
	 		RAService.searchResourceGeneral(primarySkills9,jobCategory9,jobLocation9,yearsOfExperience9,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
				 $scope.errormsg=data.errorCode;
				 if($scope.errormsg=="OK"){
				   $scope.list = data.result;
				   console.log($scope.list);
				   $scope.totalCount = data.count;
				   $scope.divshowing_cus1=true;
				   $scope.divshowing_cus=false;
				   $scope.divLoadPagination=false;
					$scope.divSearchPagination=true;
					$scope.divFilterPagination=false;
				 }	 if($scope.errormsg=="NO_CONTENT"){
					 console.log("no data found");
					 $scope.divshowing_cus=true;
					 $scope.divshowing_cus1=false;
				 }
				
			   })	
							}
	 	
	 	 $scope.sidefilterlist=function()
						       {
						    	 
						    	   RAService.getsidefiltersAllResource_customer().then(function(data) {
						                  $scope.skillslist = data.result;
						                 
						                  console.log($scope.skillslist);
						                
						               
						        }, function(err) {
						               if (err) {
						                      $scope.errorMessage = err;
						               }
						        })
						 
						    	   
						       }
	 	
	 	$scope.pageChanged = function() {
			$scope.getresourcelist()
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
					 $scope.getresourcelist();
				 }
				 else{
					 RAService.searchResourceGeneral(primarySkills,jobCategory,jobLocation,experience,$scope.pageIndex,$scope.pageSizeSelected).then(function(data){
						 $scope.errormsg=data.errorCode;
						 if($scope.errormsg=="NO_CONTENT"){
							 console.log("no data found");
							 $scope.divshowing_cus=true;
							 $scope.divshowing_cus1=false;
						 }
						 if($scope.errormsg=="OK"){
						   $scope.resourcelist = data.result;
						   console.log($scope.resourcelist);
						   $scope.totalCount = data.count;
						   $scope.divshowing_cus1=true;
						   $scope.divshowing_cus=false;
						   $scope.divLoadPagination=false;
							$scope.divSearchPagination=true;
							$scope.divFilterPagination=false;
						 }
						
					   })			
				}
			 }
	 	$scope.skills=["java","jsp","servlets","Spring","Html","Css","Bootstrap","Angularjs","Nodejs","Php","Phyton","MySQL","MongoDB","Oracle","Sql Server"];
	 	$scope.allListsfunc= function(){
			debugger;
	 		/*RAService.allresLists().then(function(data){
	 			$scope.skillslist = data.allList; 	       
	 	    });*/
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
				
				if($scope.skil.skills == "undefined"&&$scope.job.jobCategory1 == "undefined"&&$scope.exp2.totalExperience == "undefined"&&	$scope.location2.city == "undefined"
					&&$scope.customer1.customer == "undefined"&&$scope.budget1.budget == "undefined"){
					$scope.getresourcelist();
				}
				else{
				debugger;
				RAService.searchsidefilterresourcecutomer(
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
								 $scope.divshowing_cus=true;
								 $scope.divshowing_cus1=false;
							 }
							 if($scope.errormsg=="OK"){
								 $scope.list = data.result;
									console.log($scope.list);
									$scope.totalCount = data.count;
									 $scope.divshowing_cus=false;
									 $scope.divshowing_cus1=true;
									$scope.divLoadPagination=false;
									$scope.divSearchPagination=false;
									$scope.divFilterPagination=true;
							 }
							
						})

			}
			}
 	
	 $scope.requestResource = function(_id,vendor_registrationId){
		 debugger;
		 var id  = _id  
		 var vendor_registrationid =vendor_registrationId;
		 var customer_requirementId=$rootScope.requirementid;	 	
	 	 var customer_registrationId =$rootScope.customer_registrationId;
	 	
	 	//var localregistrationId =   localStorage.getItem('registrationId');
	 	debugger;
	 	RAService.requestResourcevendor(customer_requirementId,id,customer_registrationId,vendor_registrationid).then(function(data){
		 		//$scope.resourcelist = data;
	 		debugger;
		 		console.log(data)
		 		alert("success");
		 	})	
	 	}
	
	 
	 
//	 	RAService.searchsidefilterresource(skills,jobCategory,jobLocation,experience,vendors,budget).then(function(data){
//	 		debugger;
//	 		$scope.resourcelist = data.result;
//	 		console.log($scope.resourcelist)	
//	 	})
	 	
}]);
resourceApp.controller('customergetresourceCtrl',["$scope","$state","$stateParams","$filter","RAService",function($scope, $state, $stateParams, $filter,RAService) {
	$scope.$on('$viewContentLoaded', function() {
		$scope.resource = {};
		
		$scope.getResourceById();
	})
	

	$scope.gender = [ "Male", "Female", "Transgender" ];
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
							$scope.skills=["java","jsp","servlets","Spring","Html","Css","Bootstrap","Angularjs","Nodejs","Php","Phyton","MySQL","MongoDB","Oracle","Sql Server"];
	$scope.avaliability = [ "10-20 days", "20-30 days","30-45 days", "45-60 days" ];
	$scope.getResourceById = function() {
			RAService.resourcegetById($stateParams.resourceId).then(function(data) {
							$scope.resource = data.result;
							
							$scope.resource.dateOfBirth = new Date(
							$scope.resource.dateOfBirth);
							console.log($scope.resouce);
					},
						function(err) {
							if (err) {
								$scope.errorMessage = err;
							}
						})
	}



} ]);
