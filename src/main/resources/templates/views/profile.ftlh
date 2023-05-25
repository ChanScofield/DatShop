<#import "/spring.ftl" as spring>

<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/style.css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <script src="/script.js"></script>

</head>
<body>
<#include "*/parts/_menu.ftlh">
<div class="container rounded bg-white mt-5 mb-5">
    <div class="row"  id="profile-form">
        <div class="col-md-3 border-right">
            <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                <img class="rounded-circle mt-5" width="150px"
                     src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg">
                <span class="font-weight-bold">${userProfile.userName}</span>
                <span class="text-black-50">${userProfile.email}</span>
                <br>

                <form action="/profile/donate" method="post">
                    <span class="font-weight-bold">Balance: ${userProfile.balance} $</span><br>
                    <input type="number" name="donate" class="form-control" placeholder="Donate" required><br>
                    <#if errorDonate??>
                        <p class="ui-state-error-text">${errorDonate}</p>
                    </#if>
                    <#if niceDonate??>
                        <p class="ui-state-active-text">${niceDonate}</p>
                    </#if>

                    <button type="submit" class="btn btn-success">
                        <i class="material-icons"></i>
                        <span>Donate</span></button>
                </form>


            </div>
        </div>

        <form action="/profile/save-profile" method="post" class="col-md-5 border-right">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">Profile Settings</h4>
                </div>
                <div class="row mt-3">

                    <div class="col-md-12">
                        <@spring.bind "changeProfileDto.username"/>
                        <label class="labels">Username</label>
                        <input type="text" name="username" class="form-control" value="${userProfile.userName}"
                               placeholder="Username">

                        <p class="ui-state-error-text">
                            <@spring.showErrors "<br/>"/>
                        </p>
                    </div>

                    <div class="col-md-12">
                        <@spring.bind "changeProfileDto.phoneNumber"/>
                        <label class="labels">Mobile Number</label>
                        <input type="tel" name="phoneNumber" class="form-control"
                               placeholder="Phone number: 8 xxx xxx xx xx" value="${userProfile.phoneNumber}">

                        <p class="ui-state-error-text">
                            <@spring.showErrors "<br/>"/>
                        </p>
                    </div>

                    <div class="col-md-12">
                        <label class="labels">City</label>
                        <@spring.bind "changeProfileDto.city"/>

                        <select name="city" class="form-control">
                            <#list cities as city>
                                <option value="${city}" <#if userProfile.city == city>selected</#if>>
                                    ${city}
                                </option>
                            </#list>
                        </select>

                        <p class="ui-state-error-text">
                            <@spring.showErrors "<br/>"/>
                        </p>
                    </div>

                    <div class="col-md-12">
                        <label class="labels">Sex </label> <br>
                        <@spring.bind "changeProfileDto.sex"/>

                        <select name="sex" class="form-control">
                            <option value="${userProfile.sex}" <#if userProfile.sex == "Male">selected</#if>>
                                Male
                            </option>
                            <option value="${userProfile.sex}" <#if userProfile.sex == "Female">selected</#if>>
                                Female
                            </option>
                        </select>

                        <p class="ui-state-error-text">
                            <@spring.showErrors "<br/>"/>
                        </p>
                    </div>

                    <div class="col-md-12">
                        <@spring.bind "changeProfileDto.age"/>
                        <label class="labels">Age</label>
                        <input type="number" name="age" class="form-control"
                               placeholder="Age" value="${userProfile.age}">

                        <p class="ui-state-error-text">
                            <@spring.showErrors "<br/>"/>
                        </p>
                    </div>
                </div>

                <div class="mt-5 text-center">
                    <#if changeSuccess??>
                        <p class="ui-state-active-text">${changeSuccess}</p>
                    </#if>
                    <button class="btn btn-primary profile-button" type="submit">Save Profile</button>
                </div>
            </div>
        </form>

        <#if auth == "LOCAL">
            <#include "*/parts/_change_password_form.ftlh">
        </#if>

    </div>
</div>
<#include "*/parts/_footer.ftlh">
</body>
</html>
