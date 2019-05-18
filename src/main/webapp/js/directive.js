resourceApp.directive('foot',function(){
	return {
		templateUrl:'partials/footer.html',
		restrict:'EAC',
		replace:true,
		scope:{

		}
	};
});
resourceApp.directive( 'creditCardType', function(){
    var directive1 ={ 
    		require: 'ngModel',
    		link: function(scope, elm, attrs, ctrl){
           ctrl.$parsers.unshift(function(value){
            scope.card.type =
              (/^5[1-5][0-9]{14}/.test(value)) ? "Mastercard"
              : (/^4[0-9]{12}(?:[0-9]{3})/.test(value)) ? "Visa"
              : (/^6[0-9]{15}/.test(value)) ? 'Rupay'
				: (/^(5018|5020|5038|5044|6304|6759|6761|6763)[0-9]{8,15}/.test(value)) ? 'Maestro'
              : (/^(4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})/.test(value)) ? 'Visa Master Card'
              : undefined
            ctrl.$setValidity('invalid',!!scope.card.type)
            return value
           }, true)
        }
      }
    return directive1
    }
  );

resourceApp.directive('cardExpiration', function(){
    var directive ={
    		require: 'ngModel', 
    		link: function(scope, elm, attrs, ctrl){
          scope.$watch('[card.month,card.year]',function(value){
            ctrl.$setValidity('invalid',true)
            if ( scope.card.year == scope.currentYear
                 && scope.card.month <= scope.currentMonth
               ) {
              ctrl.$setValidity('invalid',false)
            }
            return value
          },true)
        }
      }
    return directive
    }
  );

