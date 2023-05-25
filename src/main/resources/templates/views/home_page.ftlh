<#include "*/parts/_home_body.ftlh">

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-6 mx-auto">
            <div class="input-group mb-3 custom-search">
                <input id="searchInput" type="text" class="form-control form-control-sm border-0"
                       placeholder="Search for books" onkeyup="checkInput(this, liveSearch)">
            </div>
        </div>
    </div>
    <div class="row">
        <#include "*/parts/_left.ftlh">
        <div class="col-sm-9">
            <div id="content" class="row">
                <#list listBook as book>
                    <div class="product col-12 col-md-6 col-lg-4">
                        <div class="card my-card">
                            <div class="heart"></div>
                            <a href="/home/show-book?bid=${book.id}" title="${book.title}">
                                <img class="" src="${book.image}" alt="Book image">
                            </a>
                            <div class="card-body">
                                <h4 class="card-title show_txt">
                                    <a href="/home/show-book?bid=${book.id}" title="${book.title}">${book.title}</a>
                                </h4>
                                <p class="card-text show_txt">${book.description}</p>
                                <div class="row">
                                    <div class="col">
                                        <a href="/cart/add-to-cart?bid=${book.id}"
                                           class="btn btn-danger btn-block">${book.price} $</a>
                                    </div>
                                    <div class="col">
                                        <button onclick="addToCart(${book.id})" class="btn btn-success btn-block">
                                            Add to cart
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>

            </div>

            <button onclick="loadMore('${page}')" class="btn btn-primary" id="btvn">
                Load More
            </button>

            <button onclick="up()" class="btn btn-primary" id="up">Top</button>


        </div>

    </div>
</div>

<#include "*/parts/_footer.ftlh">
</body>
</html>
