angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope) {})

.controller('ChatsCtrl', function($scope, Chats) {
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});
  
  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  }
})

.controller('ChatDetailCtrl', function($http, $scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
  $scope.output1 = "1";
  
   $scope.clickAlert = function(){
     alert("hi");
   };
  
  $scope.httpRequest = function(){
    $http.get('http://www.baidu.com/').success(function(data){
      $scope.output1 = "2";
      $scope.output = data;
      $scope.output1 = "3";
    });
  };
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});
