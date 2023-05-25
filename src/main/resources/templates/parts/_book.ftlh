<#list listBook as book>
    <div class="product col-12 col-md-6 col-lg-3">
        <div class="card my-card">
            <a href="/google_api/show-book?bid=${book.id}" title="${book.title}">
                <img class="" src="${book.image}" alt="Book image">
            </a>
            <div class="card-body">
                <h4 class="card-title show_txt">
                    <a href="/google_api/show-book?bid=${book.id}" target="_blank" title="${book.title}">${book.title}</a>
                </h4>
                <p class="card-text show_txt">${book.description}</p>
                <div class="row">
                    <div class="col">
                        <a href="${book.webReaderLink}"
                           class="btn btn-danger btn-block" target="_blank">Reading Online</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#list>