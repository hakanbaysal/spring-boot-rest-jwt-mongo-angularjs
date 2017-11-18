'use strict';

app.service('AuthService', ['$http', function ($http, ) {

    var signUpUrl = '/auth/sign-up';

    this.signUp = function (user) {
        return $http.post(signUpUrl, user);
    };

    var loginUrl = '/login'
    this.login = function (user) {
        return $http.post(loginUrl, user)
            .success(function (data, status, headers, config) {
                var header = headers();
                sessionStorage.setItem('Authorization', header.authorization);
            })
            .error(function (data, status, headers, config) {
            })
    };

    var forgotPasswordUrl = '/auth/forgot-password'
    this.forgotPassword = function (email) {
        return $http.post(forgotPasswordUrl, email);
    };

}]);