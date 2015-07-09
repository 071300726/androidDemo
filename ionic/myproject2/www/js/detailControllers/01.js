angular.module('starter.controllers').controller('Level01Ctrl', function($scope) {

	var context = $scope.$parent.levelContext;
	
	context.onStart=function(){
		console.log("start!");
	};
	context.onEnd=function(){
		console.log("End!");
	};
	context.getScore=function(){
		return $scope.sliderVal;
	};
	
});