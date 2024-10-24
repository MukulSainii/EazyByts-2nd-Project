'use strict';

let token = document.querySelector('meta[name="_csrf"]').content;

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {
        $scope.orders = [];

        $scope.getItems = function () {
            $http({
                method: "GET",
                url: "/api/orders/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.orders = data.data;
                    if ($scope.orders[0] == null) {
                        document.querySelector("#errorMsg").innerHTML = 'Order list is empty!';
                        document.querySelector("#dishes_block").className = 'hidden';
                    }
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }

        $scope.getAllItems = function () {
            $http({
                method: "GET",
                url: "/api/orders/getAll",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.orders = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        $scope.itemId = null;
        $scope.confirmAction = function (event) {
            let id = event.currentTarget.getAttribute('id');
            console.log(id);
            let object = { "itemId": id }
            console.log(object);
            $http({
                method: "PUT",
                url: "/api/orders/confirm",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                // if (response.data) {
                    location.reload();
                // }
            }, function (response) {
                alertErrors(response);
                console.log(response.status);
            });
        };

    }]);



// let init = () => {
//     let items = document.querySelectorAll('.rows');
//     items.forEach((elem) => {
//         let tags = elem.getElementsByTagName("td");
//         if (tags[2].innerHTML === 'NEW') {
//             let link = elem.getElementsByTagName('a');
//             link[0].className = 'button';
//         }
//     });
// }
// window.onload = init;