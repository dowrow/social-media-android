package com.dowrow.socialmedia.controllers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.dowrow.socialmedia.models.SocialMediaAPI;
import com.dowrow.socialmedia.models.SocialMediaService;
import com.dowrow.socialmedia.views.GlobalFeedActivity;
import com.dowrow.socialmedia.views.LoginActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginController {

    private LoginActivity loginActivity;

    private static LoginController instance = null;

    private  FacebookLoginController facebookLoginController;

    private  TwitterLoginController twitterLoginController;

    private SocialLoginController currentSocialLoginController;

    protected void onSocialControllerSuccess(SocialLoginController currentSocialLoginController) {

        final ProgressDialog progress = new ProgressDialog(loginActivity);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

        this.currentSocialLoginController = currentSocialLoginController;

        SocialMediaService service = new SocialMediaAPI().getService();

        service.getMe().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progress.dismiss();
                Intent intent = new Intent(loginActivity, GlobalFeedActivity.class);
                loginActivity.startActivity(intent);
                Toast toast = Toast.makeText(loginActivity, "Welcome " + response.body(), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progress.dismiss();
                Toast toast = Toast.makeText(loginActivity, "The server failed to respond.", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    protected void onSocialControllerError() {
        Log.d("Social controller error", ":(");
        Toast toast = Toast.makeText(loginActivity, "Wrong user or password. Try again.", Toast.LENGTH_LONG);
        toast.show();
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

    public String getAuthorizationHeader() {
        return this.currentSocialLoginController.getAuthorizationHeader();
    }
}
