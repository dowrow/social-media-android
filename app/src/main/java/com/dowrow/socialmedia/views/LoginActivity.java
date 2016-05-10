package com.dowrow.socialmedia.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.LoginController;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginController.getInstance().initializeSdks(this);
        setContentView(R.layout.activity_login);
        LoginController.getInstance().configureLogins(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LoginController.getInstance().onActivityResult(requestCode, resultCode, data);
    }

}
