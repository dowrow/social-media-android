package com.dowrow.socialmedia.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.LoginController;

public class LoginActivity extends AppCompatActivity {

    LoginController loginController;

    public LoginActivity() {
        loginController = LoginController.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginController.initializeSdks(this);
        setContentView(R.layout.activity_login);
        loginController.configureLogins(this);
        if (loginController.isSessionStored()) {
            loginController.loadStoredSession();
            loginController.login();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginController.onActivityResult(requestCode, resultCode, data);
    }

}
