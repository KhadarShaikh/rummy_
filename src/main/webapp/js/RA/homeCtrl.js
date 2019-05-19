resourceApp.controller("homeCtrl",["$scope","$state","$http","$stateParams","RAService",function($scope,$http , $state, $stateParams, RAService){
	
	
$scope.$on('$viewContentLoaded', function () {
	/*$scope.topCustomers();
	    $scope.topVendors();
		$scope.getPieSkills();
	  $scope.getPierecSkills();
		$scope.getPieAvbSkills();*/
    });
    $scope.topCustomers = function(){
    	debugger;
    	RAService.getTopCustomerslist().then(function(data){
    	
			$scope.list1 = data.list;
			console.log($scope.list1);
			 
			
		})
		
    }
    $scope.topVendors = function(){
    	debugger;
      
        	RAService.getTopVendorslist().then(function(data){
        	
    			$scope.topvendor1 = data.topvendor;
    			console.log($scope.topvendor1);
    			
    		});
    		
       }
    // Requirement vs Skills 
   $scope.getPieSkills = function() {
        
        RAService.getRequirementvsSkills().then(
                function(data) {
                    $scope.piedata = data.result;
                    console.log($scope.piedata);
                    $scope.mypiedata($scope.piedata);
                    
                })
    }
  $scope.mypiedata = function(object){ 


$scope.chartOps = {
		 palette :  ['#e8a742', '#e49316', '#285484', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	        
    dataSource: object,
    title: "Requirement Vs Job Category",
    
    "export": {
        enabled: false
    },
    tooltip: {
        enabled: true,
        location: "edge",
        customizeTooltip: function (arg) {
            return {
                text:  " Total: " + arg.valueText
            };
        }
    },
    series: [{
        argumentField: "label",
        valueField: "value",
        label: {
            visible: true,
            connector: {
                visible: true,
                width: 0.5
            },
            format: "fixedPoint",
            customizeText: function (point) {
                return point.argumentText + ": " + point.valueText + "";
            }
        },
        smallValuesGrouping: {
            mode: "smallValueThreshold",
            threshold: 0.5
        }
    }]
};
}
  
  //end of Req vs Skills
  
  // resource vs skills
  
 $scope.getPierecSkills = function() {
   
      RAService.getResourcevsSkills().then(
              function(data) {
                  $scope.recdata = data.result;
                  console.log($scope.recdata);
                  $scope.myrecdata($scope.recdata);
                  
              })
  }
$scope.myrecdata = function(object){ 
$scope.chartOps1 = {
		 palette :  ['#e8a742', '#e49316', '#285484', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],	     
  dataSource: object,
  title: "Resource Vs Job Category",
  "export": {
      enabled: true
  },
  tooltip: {
      enabled: true,
      location: "edge",
      customizeTooltip: function (arg) {
          return {
              text:  " Total: " + arg.valueText
          };
      }
  },
  series: [{
      argumentField: "label",
      valueField: "value",
      label: {
          visible: true,
          connector: {
              visible: true,
              width: 0.5
          },
          format: "fixedPoint",
          customizeText: function (point) {
              return point.argumentText + ": " + point.valueText + "";
          }
      },
      smallValuesGrouping: {
          mode: "smallValueThreshold",
          threshold: 0.5
      }
  }]
};
} 
  
  //end of resource vs skills
//Availability of resources

$scope.getPieAvbSkills = function() {
  debugger;
     RAService.getAvailability().then(
             function(data) {
                 $scope.availability = data.result;
                 console.log($scope.availability);
                 $scope.myavbdata($scope.availability);
                 
             })
 }
$scope.myavbdata = function(object){ 
$scope.chartOps2 = {
 dataSource: object,
 palette :  ['#e8a742', '#e49316', '#285484', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
 title: "Availability of resources",
 "export": {
     enabled: false
 },
 tooltip: {
     enabled: true,
     location: "edge",
     customizeTooltip: function (arg) {
         return {
             text:  " Total: " + arg.valueText
         };
     }
 },
 commonSeriesSettings: {
     argumentField: "Availability",
     valueField: "resoureces",
    	 type: "bar",
    	  ignoreEmptyPoints: true,
      	 name: "Resources"
 },
 seriesTemplate: {
     nameField: "Availability"
 }
};
}
 
 //end of availabilty 
}]);
    	
   
		
    
   
    	    
  


