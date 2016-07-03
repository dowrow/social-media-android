package com.dowrow.socialmedia.controllers.sociallogin;

import android.content.Intent;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.LoginController;
import com.dowrow.socialmedia.views.activities.LoginActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class TwitterLoginController implements SocialLoginController {

    TwitterLoginButton twitterLoginButton;

    String accessToken;

    String accessTokenSecret;

    @Override
    public void initializeSdk(LoginActivity activity) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                activity.getString(R.string.twitter_app_id),
                activity.getString(R.string.twitter_api_key)
        );
        Fabric.with(activity, new Twitter(authConfig), new Crashlytics(), new CrashlyticsNdk());
    }

    @Override
    public void configureLogin(LoginActivity activity, final LoginController loginController) {
        final SocialLoginController thisInstance = this;
        twitterLoginButton = (TwitterLoginButton) activity.findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d("Twitter login", "SUCCESS. Access token = " + result.data.getAuthToken().token);
                accessToken = result.data.getAuthToken().token;
                accessTokenSecret = result.data.getAuthToken().secret;
                loginController.onSocialControllerSuccess(thisInstance);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("Twitter login", "ERROR: " + exception.toString());
                Log.d("Twitter login", exception.getMessage());
                loginController.onSocialControllerError();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getAuthorizationHeader() {
        return "Bearer twitter oauth_token=" + accessToken + "&oauth_token_secret=" + accessTokenSecret;
    }

    @Override
    public void logOut() {
        Twitter.getSessionManager().clearActiveSession();
    }

}
