package com.dowrow.socialmedia.controllers.sociallogin;

import android.content.Intent;

import com.dowrow.socialmedia.controllers.LoginController;
import com.dowrow.socialmedia.views.LoginActivity;

public interface SocialLoginController {

    void initializeSdk(LoginActivity activity);

    void configureLogin(LoginActivity activity, LoginController loginController);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    String getAuthorizationHeader();

    void logOut();

}
