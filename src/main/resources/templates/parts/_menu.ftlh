<style>
    .nav-link, .navbar-brand {
        color: white;
    }

    a:hover {
        color: black;
    }

    form {
        display: block;
        margin-block-end: 0em;
    }
</style>

<nav class="navbar navbar-expand-md navbar-blue text-light">
    <div class="container">
        <a class="navbar-brand" href="/home"> DatBookShop </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
            <ul class="navbar-nav m-auto">

                <#if logged??>
                    <#include "*/parts/${logged}.ftlh">
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/sign-in">Login</a>
                    </li>
                </#if>

            </ul>

            <div class="form-inline my-2 my-lg-0">
                <form action="/cart" method="get">
                    <button type="submit" class="btn btn-success btn-sm ml-3" onclick="">
                        <i class="fa fa-shopping-cart"></i> Cart
                        <span class="badge badge-light"
                              id="count-product"><#if countProduct??>${countProduct}</#if></span>
                    </button>
                </form>

                <a href="/google_api" class="nav-link">Don't see what you want?</a>

            </div>

        </div>
    </div>
</nav>

<section class="jumbotron text-center" style="margin-bottom: 1rem">
    <div class="container">
        <h1 class="jumbotron-heading"> Dat Book Shop </h1>
        <p class="lead text-muted mb-0"> Beautiful books at affordable prices and an unforgettable experience </p>
    </div>
</section>

