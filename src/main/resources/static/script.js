function loadMore(param) {
    let url = new URL(document.URL);
    let id = url.searchParams.get("cid");
    let amount = document.getElementsByClassName("product").length;
    $.ajax({
        url: "/home/" + param,
        type: "post",
        data: {
            exits: amount,
            cId: id
        },
        success: function (response) {
            if (response.toString().length === 0) {
                let element = document.getElementById("btvn");
                element.setAttribute("hidden", "hidden");

            } else {
                let row = document.getElementById("content");
                row.innerHTML += response;
            }
        },
        error: function (xhr) {

        }
    });
}

function checkInputAndItems(callback) {
    let input = document.getElementById("searchInput");
    if (input.value.trim() !== '') {
        callback(input);
    }
}

function loadMoreByGoogleApi() {
    let amount = document.getElementsByClassName("product").length;
    let searchInput = document.getElementById("searchInput");
    let search = searchInput.value;

    $.ajax({
        url: "/google_api/load-more-google-api",
        type: "post",
        data: {
            exits: amount,
            query: search
        },
        success: function (response) {
            if (response.toString().length === 0) {
                let element = document.getElementById("btvn");
                element.setAttribute("hidden", "hidden");

            } else {
                let row = document.getElementById("content");
                row.innerHTML += response;
            }
        },
        error: function (xhr) {

        }
    });
}

function loadPage(page) {
    $.ajax({
        url: "/manage-product/" + page,
        type: "get",
        success: function (response) {
            let script = response.split("||");

            let row = document.getElementById("content");
            row.innerHTML = script[0];

            let previous = document.getElementById("previous");
            previous.className = script[1];

            let page0 = document.getElementById("page0");
            page0.setAttribute("onclick", "loadPage(" + (parseInt(script[6]) - 1) + ")");

            let page1 = document.getElementById("page1");
            page1.setAttribute("onclick", "loadPage(" + script[2] + ")");
            page1.innerHTML = script[2];
            if (parseInt(script[2]) === parseInt(page)) {
                document.getElementById("list1").className = "page-item active";
            } else {
                document.getElementById("list1").className = "page-item";
            }

            let page2 = document.getElementById("page2");
            page2.setAttribute("onclick", "loadPage(" + script[3] + ")");
            page2.innerHTML = script[3];
            if (parseInt(script[3]) === parseInt(page)) {
                document.getElementById("list2").className = "page-item active";
            } else {
                document.getElementById("list2").className = "page-item";
            }

            let page3 = document.getElementById("page3");
            page3.setAttribute("onclick", "loadPage(" + script[4] + ")");
            page3.innerHTML = script[4];
            if (parseInt(script[4]) === parseInt(page)) {
                document.getElementById("list3").className = "page-item active";
            } else {
                document.getElementById("list3").className = "page-item";
            }

            let page4 = document.getElementById("page4");
            page4.setAttribute("onclick", "loadPage(" + (parseInt(script[6]) + 1) + ")");

            let next = document.getElementById("next");
            next.className = script[5];
        }
    })
}

let toggle = button => {
    let element = document.getElementById("list-category-2");
    let hidden = element.getAttribute("hidden");

    if (hidden) {
        element.removeAttribute("hidden");
        button.innerText = "Hide list";
    } else {
        element.setAttribute("hidden", "hidden");
        button.innerText = "Show list";
    }
}
let timeoutId;


function checkInput(input, callback) {
    if (input.value.trim() !== '') {
        callback(input);
    }
}

function liveSearch(param) {
    let search = param.value;
    clearTimeout(timeoutId);

    let pathName = window.location.pathname;
    let path = pathName.split("/")[1];

    timeoutId = setTimeout(function () {
        $.ajax({
            url: "/" + path + "/live-search",
            type: "post",
            data: {
                query: search
            },
            success: function (response) {
                let row = document.getElementById("content");
                row.innerHTML = response
            }
        })
    }, 800);
}

window.onpopstate = function (event) {
    liveSearch(document.getElementById("searchInput"))
};

function addToCart(param) {
    $.ajax({
        url: "/cart/add-to-cart",
        type: "post",
        data: {
            bookId: param
        },
        success: function (response) {
            let row = document.getElementById("count-product");
            row.innerText = response;
        }
    })
}


function update(param, price, id) {
    let amount = document.getElementById("amount" + id).value;
    let calculate = price * amount;
    document.getElementById("price" + id).innerText = (calculate) + " $";

    let arr = document.getElementsByClassName("sumne");
    let total = 0;

    for (let i = 0; i < arr.length; i++) {
        total += parseInt(arr[i].innerHTML);
    }

    document.getElementById("subtotal").innerHTML = "$" + total;

    let ship = parseFloat(document.getElementById("3").id);

    document.getElementById("total").innerText = "$" + (total + ship);
    document.getElementById("check-out").innerText = "$" + (total + ship);

    $.ajax({
        url: "update_cart_controller",
        type: "post",
        data: {
            amount: amount,
            bid: id
        }, success: function (response) {
            let row = document.getElementById("pay");
            if (document.getElementById("sure") == null) {
                row.innerHTML += response;
                let row2 = document.getElementById("hid");
                row2.remove();
            }
        }
    })
}

function up() {
    window.scroll({
        top: 0,
        behavior: "smooth",
    });
}


