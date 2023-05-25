<form action="/profile/change-password" method="post" class="col-md-4">
    <div class="p-3 py-5">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="text-right">Change password</h4>
        </div>
        <div class="row mt-3">

            <div class="col-md-12">
                <@spring.bind "changePasswordDto.oldPassword"/>
                <label class="labels">Password</label>
                <input type="password" name="oldPassword" class="form-control"
                       placeholder="Old password">
                <p class="ui-state-error-text">
                    <@spring.showErrors "<br/>"/>
                </p>
            </div>

            <div class="col-md-12">
                <@spring.bind "changePasswordDto.newPassword"/>
                <label class="labels">New password</label>
                <input type="password" name="newPassword" class="form-control"
                       placeholder="New password">
                <p class="ui-state-error-text">
                    <@spring.showErrors "<br/>"/>
                </p>
            </div>

            <div class="col-md-12">
                <@spring.bind "changePasswordDto.repeatNewPassword"/>
                <label class="labels">Repeat new password</label>
                <input type="password" name="repeatNewPassword" class="form-control"
                       placeholder="Repeat new password">
                <p class="ui-state-error-text">
                    <@spring.showErrors "<br/>"/>
                </p>
            </div>

        </div>

        <div class="mt-5 text-center" style="margin-top: unset">
            <#if passwordChanged??>
                <p class="ui-state-active-text">${passwordChanged}</p>
            </#if>
            <button class="btn btn-primary profile-button" type="submit">Change</button>
        </div>
    </div>

</form>