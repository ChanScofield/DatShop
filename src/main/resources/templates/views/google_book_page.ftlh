<#include "*/parts/_home_body.ftlh">

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-6 mx-auto">
            <div class="input-group mb-3 custom-search">
                <input id="searchInput" type="text" class="form-control form-control-sm border-0"
                       placeholder="Search for books" onkeyup="checkInput(this, liveSearch)">
            </div>
        </div>
        <div class="col-sm-12">
            <div id="content" class="row">

                <#--    books       -->

            </div>


            <button onclick="checkInputAndItems(loadMoreByGoogleApi)" class="btn btn-primary" id="btvn">Load More</button>
            <button onclick="up()" class="btn btn-primary" id="up">Top</button>


        </div>


    </div>
</div>

<#include "*/parts/_footer.ftlh">
</body>
</html>
