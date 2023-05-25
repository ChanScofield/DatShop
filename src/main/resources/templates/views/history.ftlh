<html>
<head>
    <title>History</title>
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
                    <h2>Purchase <b>History</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="/home" class="btn btn-danger">
                        <span>Dat Book Shop</span>
                    </a>

                    <#if global??>
                        <a href="/purchase-history" class="btn btn-danger">
                            <span>Purchase History</span>
                        </a>
                    </#if>
                    <#if logged == "_admin">
                        <a href="/purchase-history/global-history" class="btn btn-danger">
                            <span>Global Purchase History</span>
                        </a>
                    </#if>

                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Id Order</th>
                <th>Time Order</th>
                <#if global??>
                    <th>Buyer</th>
                </#if>
                <th>Price</th>
                <th>View</th>
            </tr>
            </thead>
            <tbody id="content">
            <#list listOrders as order>
                <tr class="product">
                    <td>${order.id}</td>
                    <td>${order.date}</td>
                    <#if global??>
                        <td>${order.userName}</td>
                    </#if>
                    <td>${order.price} $</td>
                    <td>
                        <a href="/purchase-history/details?orderId=${order.id}" class="edit">
                            <i class="material-icons" title="Edit">&#xE254;</i>
                        </a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>
