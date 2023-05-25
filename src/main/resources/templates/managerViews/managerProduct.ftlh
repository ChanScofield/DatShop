<html>
<head>
    <title>Manager Product</title>
    <#include "*/parts/_headerManager.ftlh">
</head>
<body>
<div class="container">
    <div class="table-wrapper">

        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Manage <b>Product</b></h2>
                </div>
                <div class="col-sm-6">
                    <#if admin == true>
                        <a class="btn btn-success" data-toggle="modal" data-target="#addNewCategoryBook">
                            <i class="material-icons">&#xE147;</i>
                            <span>Add New Category Book</span></a>
                    </#if>

                    <a href="/manage-product/add-product" class="btn btn-success">
                        <i class="material-icons">&#xE147;</i>
                        <span>Add New Product</span></a>
                    <a href="/home" class="btn btn-danger">
                        <span>Dat Book Shop</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Image</th>
                <th>Price</th>
                <th>Sold</th>
                <th>Author</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody id="content">
            <#list listBook as bookDto>
                <tr class="product">
                    <td>${bookDto.id}</td>
                    <td>${bookDto.title}</td>
                    <td>
                        <img src="${bookDto.image}">
                    </td>
                    <td>${bookDto.price} $</td>
                    <td>${bookDto.quantitySold}</td>
                    <td>${bookDto.author} </td>
                    <td>${bookDto.description} </td>
                    <td>
                        <a href="/manage-product/load-product?bid=${bookDto.id}" class="edit">
                            <i class="material-icons" title="Edit">&#xE254;</i>
                        </a>
                        <a href="/manage-product/delete-product?bid=${bookDto.id}" class="delete">
                            <i class="material-icons" title="Delete">&#xE872;</i>
                        </a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <button onclick="up()" class="btn btn-primary" hidden="" id="up">Top</button>

        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center" id="pagination">

                <li class="page-item disabled" id="previous">
                    <a class="page-link" id="page0" onclick="loadPage(${page - 1})"
                       style="border-radius: 10px 0 0 10px" tabindex="-1">Previous</a>
                </li>
                <li class="page-item active" id="list1"><a id="page1" onclick="loadPage(1)" class="page-link">1</a></li>
                <li class="page-item" id="list2"><a id="page2" onclick="loadPage(2)" class="page-link">2</a></li>
                <li class="page-item" id="list3"><a id="page3" onclick="loadPage(3)" class="page-link">3</a></li>
                <li class="page-item" id="next">
                    <a class="page-link" id="page4" onclick="loadPage(${page +  1})"
                       style="border-radius: 0 10px 10px 0" tabindex="-1">Next</a>
                </li>

            </ul>
        </nav>
    </div>
</div>


<div class="modal fade" id="addNewCategoryBook" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Add Category Book</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <form action="/manage-product/add-category" method="post" style="margin-bottom: 0">
                <div class="modal-body">
                    <div class="form-group">
                        <label> Category Name</label>
                        <input name="category" type="text" class="form-control" required>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
