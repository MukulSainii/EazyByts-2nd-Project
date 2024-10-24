/**
 * check passwords match and prevents from submit if they are different
 */
let match = false;
function checkPass() {
    let password = $("#password").val();
    let confirmPassword = $("#confirmpassword").val();
    match = (password === confirmPassword);
}

$(document).ready(function () {
    $("#confirmpassword").keyup(checkPass);
});

function checkPasswordMatch() {
    if (match) {
        return true;
    } else {
        $("#confirmpassword").val('');
        alert('Passwords do not match!');
        return false;
    }
}

let token = document.querySelector('meta[name="_csrf"]').content;

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        $scope.signup = function () {
            if (!checkPasswordMatch()) {return;}
            let object = {
                'login': document.querySelector('#login').value,
                'email': document.querySelector('#email').value,
                'password': document.querySelector('#password').value
            }
            console.log(object);
            $http({
                method: "POST",
                url: "/signup",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                if (response.data) {
                    alert('Success')
                    location.replace('/login');
                }
            }, function (response) {
                alertErrors(response);
            });
        };

    }]);
