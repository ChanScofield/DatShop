
<div class="col-sm-3">
    <div class="card bg-light mb-3">
        <div class="card-header bg-primary text-white text-uppercase">
            <i class="fa fa-list"></i> Categories
        </div>
        <ul class="list-group category_block" id="list-category-1">
            <#list listCategories as category>
                <#if category.categoriesId == 6>
                    <#break>
                <#else>
                    <li class="list-group-item text-white">
                        <a href="/home/category?cid=${category.categoriesId}" class="btn btn-block">${category.categoriesName}</a>
                    </li>
                </#if>
            </#list>
        </ul>
        <ul class="list-group category_block" id="list-category-2" hidden="hidden">
            <#list listCategories as category>
            <#if category.categoriesId < 6>
                <#continue>
            <#else>
                <li class="list-group-item text-white">
                    <a href="/home/category?cid=${category.categoriesId}" class="btn btn-block">${category.categoriesName}</a>
                </li>
            </#if>
            </#list>
        </ul>
        <button onclick="toggle(this)" class="btn btn-primary">Show list</button>
    </div>

    <div class="card bg-light mb-3">
        <div class="card-header bg-danger text-white text-uppercase" style="text-align: center">
            Hot product
        </div>

        <img class="img-fluid" src="${hotBook.image}" width="100%" />
        <div class="card-body">
            <h4 class="card-title show_txt">
                <a href="/home/show-book?bid=${hotBook.id}" title="${hotBook.title}">${hotBook.title}</a>
            </h4>
            <p class="card-text show_txt">${hotBook.description}</p>
            <p class="bloc_left_price">${hotBook.price} $</p>
        </div>
    </div>

    <div class="card bg-light mb-3">
        <div class="card-header bg-success text-white text-uppercase" style="text-align: center">
            Last product
        </div>
        <img class="img-fluid" src="${lastBook.image}" width="100%" />
        <div class="card-body">
            <h4 class="card-title show_txt">
                <a href="/home/show-book?bid=${lastBook.id}" title="${lastBook.title}">${lastBook.title}</a>
            </h4>
            <p class="card-text show_txt">${lastBook.description}</p>
            <p class="bloc_left_price">${lastBook.price} $</p>
        </div>
    </div>

</div>
