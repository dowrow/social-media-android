package com.dowrow.socialmedia.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.sociallogin.FacebookLoginController;
import com.dowrow.socialmedia.controllers.sociallogin.SocialLoginController;
import com.dowrow.socialmedia.controllers.sociallogin.TwitterLoginController;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.apis.SocialMediaService;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.views.GlobalFeedActivity;
import com.dowrow.socialmedia.views.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {

    private LoginActivity loginActivity;

    private static LoginController instance = null;

    private FacebookLoginController facebookLoginController;

    private TwitterLoginController twitterLoginController;

    private SocialLoginController currentSocialLoginController;

    private boolean storedSession = false;

    private void storeSession() {
        SharedPreferences sharedPref = loginActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(loginActivity.getString(R.string.authorization_header), getAuthorizationHeader());
        editor.commit();
    }

    private void clearStoredSession() {
        SharedPreferences sharedPref = loginActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    public boolean isSessionStored() {
        SharedPreferences sharedPref = loginActivity.getPreferences(Context.MODE_PRIVATE);
        String authorizationHeader = sharedPref.getString(loginActivity.getString(R.string.authorization_header), "");
        return !authorizationHeader.isEmpty();
    }

    public String getStoredAuthorizationHeader() {
        SharedPreferences sharedPref = loginActivity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(loginActivity.getString(R.string.authorization_header), "");
    }

    public void loadStoredSession() {
        storedSession = true;
    }

    public String getAuthorizationHeader() {
        if (storedSession) {
            return this.getStoredAuthorizationHeader();
        }
        return this.currentSocialLoginController.getAuthorizationHeader();
    }

    public void onSocialControllerSuccess(SocialLoginController currentSocialLoginController) {
        this.currentSocialLoginController = currentSocialLoginController;
        storeSession();
        login();
    }

    public void onSocialControllerError() {
        Log.d("Social controller error", ":(");
        Toast toast = Toast.makeText(loginActivity, "Wrong user or password. Try again.", Toast.LENGTH_LONG);
        toast.show();
        storedSession = false;
    }

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    private LoginController() {
        facebookLoginController = new FacebookLoginController();
        twitterLoginController = new TwitterLoginController();
    }

    public void initializeSdks(LoginActivity activity) {
        facebookLoginController.initializeSdk(activity);
        twitterLoginController.initializeSdk(activity);
    }

    public void configureLogins(LoginActivity activity) {
        loginActivity = activity;
        facebookLoginController.configureLogin(activity, this);
        twitterLoginController.configureLogin(activity, this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookLoginController.onActivityResult(requestCode, resultCode, data);
        twitterLoginController.onActivityResult(requestCode, resultCode, data);
    }

    public void logOut(Activity currentActivity) {
        storedSession = false;
        clearStoredSession();
        this.facebookLoginController.logOut();
        this.twitterLoginController.logOut();
        Intent intent = new Intent(currentActivity, LoginActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }


    public void login() {
        final ProgressDialog progress = new ProgressDialog(loginActivity);
        progress.setMessage("Logging in...");
        progress.show();
        SocialMediaService service = new SocialMediaAPI().getService();
        service.getSelf().enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                    Intent intent = new Intent(loginActivity, GlobalFeedActivity.class);
                    loginActivity.startActivity(intent);
                    loginActivity.finish();
                    progress.dismiss();
                    Toast toast = Toast.makeText(loginActivity, "Welcome " + response.body().getUsername(), Toast.LENGTH_SHORT);
                    toast.show();
                } catch (Exception exception) {
                    onFailure(call, exception);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progress.dismiss();
                Toast toast = Toast.makeText(loginActivity, "The server failed to respond.", Toast.LENGTH_LONG);
                toast.show();
                logOut(loginActivity);
            }
        });
    }
}
