'use strict';

let token = document.querySelector('meta[name="_csrf"]').content;

let urlParams = new URLSearchParams(window.location.search);
let orderNo = urlParams.get('orderNo');
let totalPrice = urlParams.get('totalPrice');

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        $scope.payment = function () {
            let object = { "itemId": orderNo }
            console.log(object);
            $http({
                method: "PUT",
                url: "/api/orders/payment",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                // if (response.data) {
                    location.replace("/orders")
                // }
            }, function (response) {
                alertErrors(response);
            });
        };

    }]);

let init = () => {
    document.getElementById('orderNo').innerHTML = orderNo;
    document.getElementById('totalPrice').innerHTML = totalPrice;
}
window.onload = init;
