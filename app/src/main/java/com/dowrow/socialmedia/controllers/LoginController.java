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
import com.dowrow.socialmedia.views.activities.LoginActivity;
import com.dowrow.socialmedia.views.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {

    private LoginActivity loginActivity;

    private static LoginController instance = null;

    private List<SocialLoginController> socialLoginControllers;

    private SocialLoginController currentSocialLoginController;

    private boolean storedSession = false;

    private UserResponse self;

    private void storeSession() {
        SharedPreferences sharedPref = loginActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(loginActivity.getString(R.string.authorization_header), getAuthorizationHeader());
        editor.commit();
    }

    private void clearStoredSession() {
        if (loginActivity != null) {
            SharedPreferences sharedPref = loginActivity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
        }
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
        try {
            return this.currentSocialLoginController.getAuthorizationHeader();
        } catch (Exception e) {
            logOut(loginActivity);
            return "";
        }
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
        socialLoginControllers = new ArrayList<>();
        socialLoginControllers.add(new FacebookLoginController());
        socialLoginControllers.add(new TwitterLoginController());
    }

    public void initializeSdks(LoginActivity activity) {
        for(SocialLoginController socialLoginController : socialLoginControllers)
            socialLoginController.initializeSdk(activity);
    }

    public void configureLogins(LoginActivity activity) {
        loginActivity = activity;
        for(SocialLoginController socialLoginController : socialLoginControllers)
            socialLoginController.configureLogin(activity, this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for(SocialLoginController socialLoginController : socialLoginControllers)
            socialLoginController.onActivityResult(requestCode, resultCode, data);
    }

    public void logOut(Activity currentActivity) {
        clearStoredSession();
        storedSession = false;
        for(SocialLoginController socialLoginController : socialLoginControllers)
            socialLoginController.logOut();
        Intent intent = new Intent(currentActivity, LoginActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    public void login() {
        final ProgressDialog progress = new ProgressDialog(loginActivity);
        progress.setMessage("Logging in...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.show();
        SocialMediaService service = new SocialMediaAPI().getService();
        service.getSelf().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                    self = response.body();
                    Intent intent = new Intent(loginActivity, MainActivity.class);
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
                Log.d("error", t + " - " + t.getMessage() + " ");
                Toast toast = Toast.makeText(loginActivity, "The server failed to respond.", Toast.LENGTH_LONG);
                toast.show();
                logOut(loginActivity);
            }
        });
    }

    public UserResponse getSelf() {
        return self;
    }
}
