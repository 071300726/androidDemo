angular.module('starter.controllers', [])

.controller('WelcomeCtrl', function($scope) {})


.controller('ListCtrl', function($scope) {
	
	
})


.controller('DetailCtrl', function($scope, $stateParams) {
	
	$scope.goto = function(id){
		$scope.url = 'templates/details/'+id+'.html';
	};
	
	$scope.goto($stateParams.id);
})

.controller('Level02Ctrl', function($scope) {
	$scope.clickMe=function(){
		console.log(333);
		$scope.$parent.goto('01');
	};
	
})
;
