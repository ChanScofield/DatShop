<html>
<head>
    <title>Order Information</title>
    <#include "*/parts/_headerManager.ftlh">
    <style>
        .table {
            text-align: center;
        }
        th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="table-wrapper">

        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Order Information</h2>
                </div>
                <div class="col-sm-6">
                    <a href="/home" class="btn btn-danger">
                        <span>Dat Book Shop</span></a>
                    <a href="/purchase-history" class="btn btn-danger">
                        <span>Purchase History</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Image</th>
                <th>Title</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody id="content">
            <#list informations as info>
                <tr class="product">
                    <td>
                        <img src="${info.image}">
                    </td>
                    <td>${info.title}</td>
                    <td>${info.price} $</td>
                    <td>${info.quantity} </td>
                </tr>
            </#list>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>
