package com.dowrow.socialmedia.controllers.accounts.sociallogin;

import android.content.Intent;
import android.util.Log;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.accounts.LoginController;
import com.dowrow.socialmedia.views.activities.LoginActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookLoginController implements SocialLoginController {

    CallbackManager facebookCallbackManager;

    LoginButton facebookLoginButton;

    String accessToken = "";

    @Override
    public void initializeSdk(LoginActivity activity) {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
    }

    @Override
    public void configureLogin(LoginActivity activity, final LoginController loginController) {
        facebookCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton = (LoginButton) activity.findViewById(R.id.facebook_login_button);
        final FacebookLoginController thisInstance = this;
        facebookLoginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook login", "SUCCESS. Access token = " + loginResult.getAccessToken().getToken());
                accessToken = loginResult.getAccessToken().getToken();
                loginController.onSocialControllerSuccess(thisInstance);
            }

            @Override
            public void onCancel() {
                Log.d("Facebook login", "CANCEL");
                loginController.onSocialControllerError();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Facebook login", "ERROR: " + exception.toString());
                loginController.onSocialControllerError();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getAuthorizationHeader() {
        return "Bearer facebook " + accessToken;
    }

    @Override
    public void logOut() {
        LoginManager.getInstance().logOut();
    }

}
