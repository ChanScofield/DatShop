<html>
<head>
    <title>Edit Employee</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="/manager.css" rel="stylesheet" type="text/css"/>
    <style>
        input[type="checkbox"] {
            width: 30px;
            height: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2><b>Edit Employee</b></h2>
                </div>
            </div>
        </div>

        <div class="modal-dialog">
            <div class="modal-content">
                <form action="/manage-employees/edit-employee?eid=${user.userId}" method="post">
                    <div class="modal-body">

                        <div class="form-group">
                            <h3>Email: ${user.email}</h3>
                        </div>

                        <div class="form-group">
                            <h3>Is Seller
                                <input name="isSeller" type="checkbox" <#if user.role == "SELLER">checked</#if>>
                            </h3>
                        </div>

                        <#if user.role == "SELLER">
                            <div class="form-group">
                                <h3>
                                    <a href="/manage-employees/employee-products?eid=${user.userId}">
                                        List Book Seller
                                    </a>
                                </h3>
                            </div>
                        </#if>

                        <h4 class="ui-state-active-text"><#if mess??>${mess}</#if></h4>

                    </div>

                    <div class="modal-footer">
                        <a href="/manage-employees">
                            <input type="button" class="btn btn-default" value="Cancel">
                        </a>
                        <input type="submit" class="btn btn-info" value="Save">
                    </div>
                </form>
            </div>
        </div>


    </div>
</div>
</body>
</html>
