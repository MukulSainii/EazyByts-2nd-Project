'use strict';

let token = document.querySelector('meta[name="_csrf"]').content;

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        $scope.getItems = function () {
            $http({
                method: "GET",
                url: "/api/basket/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.dishes = data.data.dishes;
                    $scope.totalPrice = data.data.totalPrice;
                    if ($scope.dishes[0] == null) {
                        document.querySelector("#errorMsg").innerHTML = 'Basket is empty!';
                        document.querySelector("#dishes_block").className = 'hidden';
                    }
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }

        $scope.postdata = function () {
            $http({
                method: "POST",
                url: "/api/orders/create",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(function (response) {
                if (response.data) {
                    location.replace('/orders');
                }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.delete = function (event) {
            let id = event.currentTarget.getAttribute('id');
            console.log(id);
            let object = { "itemId": id }
            console.log(object);
            $http({
                method: "DELETE",
                url: "/api/basket/delete",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                console.log(response);
                window.location.reload();
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.deleteAll = function () {
            $http({
                method: "DELETE",
                url: "/api/basket/deleteAll",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(function (response) {
                console.log(response);
                window.location.reload();
            }, function (response) {
                alertErrors(response);
            });
        };

    }]);
