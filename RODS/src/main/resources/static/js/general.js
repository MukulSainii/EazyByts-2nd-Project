'use strict';

/**
 * output error responce
 */
let alertErrors = (response) => {
    console.log(response);
    let msg = '';
    if (response.data.errors === undefined) {
        msg = response.data.message;
    } else {
        for (let i in response.data.errors) {
            msg += response.data.errors[i] + "\n";
        }
    }
    alert(msg);
}

/**
 * shows 'eye' animation in passwords fields
 */
$(".toggle-password").click(function() {
    $(this).toggleClass("fa-eye fa-eye-slash");
    let input = $($(this).attr("toggle"));
    if (input.attr("type") === "password") {
        input.attr("type", "text");
    } else {
        input.attr("type", "password");
    }
});

/**
 * JS sort table fields
 */
let tableSort = (item, table) => {
    let tbl, rows, switching, shouldSwitch,
        i, n, row1, row2, direction, switchCount = 0;

    n = item.getAttribute('columns');
    tbl = document.getElementById(table);
    switching = true;
    direction = 'asc';
    while (switching) {
        switching = false;
        rows = tbl.rows;
        for (i = 1; i < (rows.length - 1); ++i) {
            shouldSwitch = false;
            row1 = rows[i].getElementsByTagName('td')[n].innerHTML.toLowerCase();
            row2 = rows[i + 1].getElementsByTagName('td')[n].innerHTML.toLowerCase();
            if ((direction === 'asc' && tableSortComparator(row1, row2)) ||
                (direction === 'desc' && tableSortComparator(row2, row1))) {
                    shouldSwitch = true;
                    break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
            switching = true;
            switchCount++;
        } else if (switchCount === 0 && direction === "asc") {
            direction = "desc";
            switching = true;
        }
    }
}
let tableSortComparator = (row1, row2) => {
    let num1 = Number.parseFloat(row1);
    let num2 = Number.parseFloat(row2);

    if (isFloatOrInteger(num1) && isFloatOrInteger(num2)) {
        return num1 > num2;
    }
    return row1 > row2;
}
function isFloatOrInteger(n) {
    return n === +n;
}
