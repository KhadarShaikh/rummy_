
	resourceApp.controller('customerdashboardCtrl',function($scope,$http,RAService){
		angular.element(document).ready(function () {
			$scope.dashdata();
		});
	  $scope.dashdata = function() {
		  $scope.isLoading = true;
			RAService.dashData().then(
					
					function(data) {
						$scope.donutchart = data.result.jobCategoryList;
						 $scope.linechart = data.result.yearList;
						 
						 $scope.line($scope.linechart);
						 $scope.mydonut($scope.donutchart);
						 $scope.isLoading = false;
						
					})
		}
	
	    $scope.mydonut = function(object){
	    	
	        Morris.Donut({
	        element: 'donut-example',
	        data:object

	        });
	    };
	  $scope.line = function(object){
		    
		    	new Morris.Line({
		        element: 'myfirstchart',
		        data:object,
		        xkey: 'label',
				  
				  ykeys: ['value'],
				 
				  labels: ['Value']
		        });
		    };
	  

	
	 Morris.Bar({
		       element: 'bar-example',
		         data: [
		          { y: '2013',a: 100, b: 90 },
		         { y: '2014', a: 50,  b: 20 },
		           { y: '2015', a: 75,  b: 40 },
		          { y: '2016', a: 60,  b: 52 },
		          { y: '2017', a: 90,  b: 75 }
		          
		         ],
		         xkey: 'y',
		        ykeys: ['a', 'b'],
		         labels: ['Requirements A', 'Resources B']
		       });
	
            });