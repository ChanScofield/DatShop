<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <link rel="stylesheet" href="/login-style.css">
</head>

<body>
<div id="logreg-forms">
    <form action="/sign-in" method="post" class="form-signin">
        <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign in</h1>
        <div class="social-login">
            <a class="button-social" href="oauth2/authorization/github">
                <button class="btn github-btn social-btn my-button" type="button">
                    <span><i class="fab fa-github"></i> Sign in with Github</span>
                </button>
            </a>
            <a class="button-social" href="oauth2/authorization/google">
                <button class="btn google-btn social-btn my-button" type="button">
                    <span><i class="fab fa-google-plus-g"></i> Sign in with Google+</span>
                </button>
            </a>
        </div>
        <p style="text-align:center"> OR </p>
        <input type="email" name="email" value="<#if email??>${email}</#if>" id="inputEmail" class="form-control"
               placeholder="Email address" required="" autofocus="">

        <input type="password" name="password" id="inputPassword" class="form-control"
               placeholder="Password" required="">

        <div class="form-group form-check" style="padding-bottom: 10px; margin: 0;">
            <input name="remember" type="checkbox" <#if remember??>checked</#if>
                   class="form-check-input"
                   id="exampleCheck1">
            <label class="form-check-label" for="exampleCheck1">Remember me</label>
        </div>

        <button class="btn btn-success btn-block" type="submit">
            <i class="fas fa-sign-in-alt">
            </i> Sign in
        </button>

        <hr>

        <a href="/sign-up" class="btn btn-block">
            <button class="btn btn-primary btn-block" type="button" id="btn-signup">
                <i class="fas fa-userProfile-plus">
                </i> Sign up New Account
            </button>
        </a>

        <hr>

        <a href="/home" methods="get" class="btn btn-block">
            <button class="btn btn-primary btn-block" type="button">
                <i class="fas fa-star">
                </i> Dat Book Shop
            </button>
        </a>

    </form>
</div>

</body>
</html>
