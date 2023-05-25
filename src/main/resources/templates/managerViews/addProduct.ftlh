<#import "/spring.ftl" as spring>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Book</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="/manager.css" rel="stylesheet" type="text/css"/>
    <style>
        textarea.form-control {
            resize: vertical;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2><b>Add Book</b></h2>
                </div>
            </div>
        </div>

        <div class="modal-dialog">
            <div class="modal-content">
                <form action="/manage-product/add-product" method="post" style="margin-bottom: 0px">
                    <div class="modal-body">

                        <div class="form-group">
                            <@spring.bind "bookDto.title"/>
                            <label>Title </label>
                            <input name="${spring.status.expression}" value="${spring.status.value!""}"
                                   type="text" class="form-control"
                                   placeholder="For example: The Alchemist, The One Thing, ...">
                            <p class="ui-state-error-text">
                                <@spring.showErrors "<br/>"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <@spring.bind "bookDto.image"/>
                            <label>Image </label>
                            <input name="${spring.status.expression}" value="${spring.status.value!""}"
                                   type="text" class="form-control"
                                   placeholder="Link! For example: https://m.media-amazon.com/images/I/81UZJqFG7VL.jpg">
                            <p class="ui-state-error-text">
                                <@spring.showErrors "<br/>"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <@spring.bind "bookDto.price"/>
                            <label>Price </label>
                            <input name="${spring.status.expression}" value="${spring.status.value!""}"
                                   type="number" class="form-control"
                                   placeholder="For example: 300, 100, ...">
                            <p class="ui-state-error-text">
                                <@spring.showErrors "<br/>"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <@spring.bind "bookDto.author"/>
                            <label>Author </label>
                            <input name="${spring.status.expression}" value="${spring.status.value!""}"
                                   class="form-control"
                                   placeholder="For example: Mario Puzo, ...">
                            <p class="ui-state-error-text">
                                <@spring.showErrors "<br/>"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <@spring.bind "bookDto.description"/>
                            <label>Description </label>
                            <textarea name="${spring.status.expression}" class="form-control"
                                      placeholder="For example: Stop worrying and take the steps to a happier, more fulfilling life!"><#if description??>${description}</#if></textarea>
                            <p class="ui-state-error-text">
                                <@spring.showErrors "<br/>"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <@spring.bind "bookDto.category"/>
                            <label>Category</label>
                            <select name="${spring.status.expression}" class="form-select" aria-label="Default select example">
                                <#list listCategory as category>
                                    <option value="${category.categoriesId}">${category.categoriesName}</option>
                                </#list>
                            </select>
                        </div>
                        <p class="ui-state-active-text"><#if Done??>${Done}</#if></p>

                    </div>
                    <div class="modal-footer">
                        <a href="/manage-product">
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
