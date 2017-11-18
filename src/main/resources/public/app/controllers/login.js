'use strict';

app.controller('LoginCtrl', function (AuthService, $scope) {
    $scope.user = new Object();

    $scope.login = function(){
        var result = AuthService.login($scope.user);
        console.log(result);
    }

});