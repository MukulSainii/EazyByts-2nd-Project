'use strict';

let token = document.querySelector('meta[name="_csrf"]').content;

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {
        $scope.dishes = [];
        $scope.getAllItems = function () {
            $http({
                method: "GET",
                url: "/api/manager/dishes/get_all",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.dishes = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        };

        $scope.categories = [];
        $scope.getCategories = function () {
            $http({
                method: "GET",
                url: "/api/categories/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.categories = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        };

        $scope.getDish = function() {
            let id;
            let urlParam = new URLSearchParams(window.location.search);
            if (urlParam.has('id')) {
                id = urlParam.get('id');
            }
            $http({
                method: "GET",
                url: "/api/manager/dishes/update?id=" + id,
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.dish = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        };

        $scope.create = function () {
            let object = {
                'id': null,
                'nameEn': document.querySelector('#name_en').value,
                'nameUa': document.querySelector('#name_ua').value,
                'price': Number.parseInt(document.querySelector('#price').value),
                'categories': {
                    'id': document.querySelector('#category').value
                }
            }
            $http({
                method: "POST",
                url: "/api/manager/dishes/create",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                if (response.data) {
                    location.replace('/manager/manage_dishes');
                }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.update = function () {
            let object = {
                'id': document.querySelector('#updateId').value,
                'nameEn': document.querySelector('#name_en').value,
                'nameUa': document.querySelector('#name_ua').value,
                'price': document.querySelector('#price').value,
                'categories': JSON.parse(document.querySelector('#updateCategories').value)
            }
            console.log(object);
            $http({
                method: "PUT",
                url: "/api/manager/dishes/update",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                // if (response.data) {
                    location.replace('/manager/manage_dishes');
                // }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.delete = function (event) {
            let id = event.currentTarget.getAttribute('dishId');
            console.log(id);
            let object = { "itemId": id }
            console.log(object);
            $http({
                method: "DELETE",
                url: "/api/manager/dishes/delete",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                window.location.reload();
            }, function (response) {
                alertErrors(response);
            });
        };
    }]);
