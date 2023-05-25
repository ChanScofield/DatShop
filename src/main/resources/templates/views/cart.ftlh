<html xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/style.css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        img {
            height: 350px;
            border-radius: 0.5rem;
        }

        .ms-4 {
            margin-left: 1.5rem;
        }

        .pe-2 {
            padding-right: 0.5rem;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        a.delete {
            color: #F44336;
        }
    </style>
    <script src="/script.js"></script>
</head>
<body>
<#include "*/parts/_menu.ftlh">
<div class="container">
    <div class="row">
        <div class="col">
            <div class="table-responsive">
                <table class="table">
                    <thead>

                    <tr>
                        <th scope="col" class="h5">Shopping Bag</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>

                    <tbody>

                    <#list listBookAdded as book>
                        <tr id="product">
                            <th scope="row">
                                <div class="d-flex align-items-center">
                                    <img src="${book.image}" class="img-fluid rounded-3"
                                         style="width: 120px;" alt="Book">
                                    <div class="flex-column ms-4">
                                        <a href="/home/show-book?bid=${book.id}">
                                            <p class="mb-2">${book.title}</p>
                                        </a>

                                        <p class="mb-0">${book.author}</p>
                                    </div>
                                </div>
                            </th>

                            <td class="align-middle">
                                <div class="d-flex flex-row">

                                    <input id="amount${book.id}"
                                           onkeyup="update(this, ${book.price}, ${book.id})"
                                           oninput="validity.valid||(value='');"
                                           value="${book.quantitySold}"
                                           name="quantity" type="number"
                                           class="form-control form-control-sm" style="width: 50px;"/>

                                </div>
                            </td>

                            <td class="align-middle">
                                <p id="price${book.id}" class="mb-0 sumne"
                                   style="font-weight: 500;">${book.price * book.quantitySold} $</p>
                            </td>

                            <td class="align-middle">
                                <a href="/cart/update-cart?bid=${book.id}" class="delete">
                                    <i class="material-icons" title="Delete">&#xE872;</i>
                                </a>
                            </td>

                        </tr>
                    </#list>

                    </tbody>
                </table>
            </div>

            <div class="card shadow-2-strong mb-5 mb-lg-0" style="border-radius: 16px;">
                <div class="card-body p-4">
                    <#if logged??>
                    <form action="/cart/buy-product?total=${total + 3}" method="post">
                        <#else>
                        <form action="/sign-in" method="get">
                            </#if>


                            <div class="row">

                                <div class="col-md-6 col-lg-4 col-xl-3 mb-4 mb-md-0">
                                    <div class="d-flex flex-row pb-3">
                                        <div class="d-flex align-items-center pe-2">
                                            <input class="form-check-input" type="radio" name="radio"
                                                   id="radioNoLabel1v"
                                                   value="" aria-label="..."/>
                                        </div>
                                        <div class="rounded border w-100 p-3">
                                            <p class="d-flex align-items-center mb-0">
                                                <i class="fa fa-cc-mastercard fa-2x text-dark pe-2"></i>Credit Card
                                            </p>
                                        </div>
                                    </div>

                                    <div class="d-flex flex-row">
                                        <div class="d-flex align-items-center pe-2">
                                            <input class="form-check-input" type="radio" name="radio"
                                                   id="radioNoLabel2v"
                                                   value="" aria-label="..." checked/>
                                        </div>
                                        <div class="rounded border w-100 p-3">
                                            <p class="d-flex align-items-center mb-0">
                                                <i class="fa fa-credit-card fa-2x fa-lg text-dark pe-2"></i>Balance
                                            </p>
                                        </div>
                                    </div>


                                </div>

                                <div class="col-md-6 col-lg-4 col-xl-6">
                                    <div class="row">
                                        <div class="col-12 col-xl-6">
                                            <div class="form-outline mb-4 mb-xl-5">
                                                <label class="form-label">Name on card</label>
                                                <input type="text" class="form-control form-control-lg" size="17"
                                                       placeholder="Dat Chan"/>
                                            </div>

                                            <div class="form-outline mb-4 mb-xl-5">
                                                <label class="form-label">Expiration</label>
                                                <input type="text" class="form-control form-control-lg"
                                                       placeholder="MM/YY"
                                                       size="7" id="exp" minlength="7" maxlength="7"/>
                                            </div>
                                        </div>
                                        <div class="col-12 col-xl-6">
                                            <div class="form-outline mb-4 mb-xl-5">
                                                <label class="form-label">Card Number</label>
                                                <input type="text" class="form-control form-control-lg" size="17"
                                                       placeholder="1111 2222 3333 4444" minlength="19" maxlength="19"/>
                                            </div>

                                            <div class="form-outline mb-4 mb-xl-5">
                                                <label class="form-label">Cvv</label>
                                                <input type="password" class="form-control form-control-lg"
                                                       placeholder="&#9679;&#9679;&#9679;" size="1" minlength="3"
                                                       maxlength="3"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-4 col-xl-3" id="pay">
                                    <div class="d-flex justify-content-between" style="font-weight: 500;">
                                        <p class="mb-2">Subtotal</p>
                                        <p id="subtotal" class="mb-2">$<#if total??>${total}</#if></p>
                                    </div>

                                    <div class="d-flex justify-content-between" style="font-weight: 500;">
                                        <p class="mb-0">Shipping</p>
                                        <p class="mb-0" id="3">$3</p>
                                    </div>

                                    <hr class="my-4">

                                    <div class="d-flex justify-content-between mb-4" style="font-weight: 500;">
                                        <p class="mb-2">Total (tax included)</p>
                                        <p class="mb-2" id="total">$<#if total??>${total + 3}</#if></p>
                                    </div>
                                    <p class="ui-state-error-text"><#if errorBuy??>${errorBuy}</#if></p>

                                    <button id="hid" type="submit" class="btn btn-primary btn-block btn-lg">
                                        <div class="d-flex justify-content-between">
                                            <span>Checkout</span>
                                            <span id="check-out">$<#if total??>${total + 3}</#if></span>
                                        </div>
                                    </button>

                                </div>
                            </div>
                        </form>
                </div>
            </div>

        </div>
    </div>
</div>

<#include "*/parts/_footer.ftlh">

</body>
</html>
