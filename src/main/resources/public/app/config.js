var app =
    angular.module('app')
        .config(
        [
            '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
            function($controllerProvider, $compileProvider, $filterProvider, $provide) {
                app.controller = $controllerProvider.register;
                app.directive = $compileProvider.directive;
                app.filter = $filterProvider.register;
                app.factory = $provide.factory;
                app.service = $provide.service;
                app.constant = $provide.constant;
                app.value = $provide.value;
            }
        ]);


app.config(function($breadcrumbProvider) {
    $breadcrumbProvider.setOptions({
        template: '<ul class="breadcrumb"><li><i class="fa fa-home"></i><a href="#">Home</a></li><li ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract"><a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a><span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span></li></ul>'
    });
});

app.factory('authHttpResponseInterceptor', ['$window', '$q', function ($window, $q) {
    return {
        response: function (response) {
            if (response.status === 401) {
                console.log(response);
            }
            return response || $q.when(response);
        },
        responseError: function (rejection) {
            if (rejection.status === 401) {
                alert("Response 401");
                $window.location.href = '/#/login';
            }
            return $q.reject(rejection);
        }
    }
}])

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
});