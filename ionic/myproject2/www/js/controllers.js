angular.module('starter.controllers', [])

.controller('WelcomeCtrl', function($scope) {})


.controller('ListCtrl', function($scope) {})


.controller('DetailCtrl', function($scope, $stateParams) {
	//load template
	$scope.templateUrl = 'templates/details/' + $stateParams.id + '.html';

	
	//should be overwrited by each level
	$scope.levelContext={
		//level start
		onStart: function(){},
		//level end
		onEnd: function(){},
		//get score
		getScore: function(){},
		
		
	};
	
	//count down to start
	var countDownToStartHandler = -1;
	var countDownToStart = function(leftSeconds){
		if(leftSeconds>0){
			console.log("start in " + leftSeconds +"s");
			leftSeconds--;
			countDownToStartHandler = setTimeout(countDownToStart,leftSeconds*1000,leftSeconds);
		}
		else{
			levelStart();
		}
	};
	
	//count down to end
	var countDownToEndHandler = -1;
	var countDownToEnd = function(leftSeconds){
		if(leftSeconds>0){
			console.log("finish in " + leftSeconds +"s");
			leftSeconds--;
			countDownToEndHandler = setTimeout(countDownToEnd,leftSeconds*1000,leftSeconds);
		}
		else{
			countDownToEndHandler = -1;
			levelEnd();
		}
	};
	$scope.endNow = function() {
		if(countDownToEndHandler>0){
			clearTimeout(countDownToEndHandler);
			countDownToEnd(0);	
		}
	}
	
	var levelStart = function(){
		countDownToEnd(5);
		$scope.levelContext.onStart();
	};
	var levelEnd = function(){
		$scope.levelContext.onEnd();
		console.log($scope.levelContext.getScore());
	};
	
	countDownToStart(3);
});
