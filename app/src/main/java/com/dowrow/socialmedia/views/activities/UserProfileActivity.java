package com.dowrow.socialmedia.views.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.UserProfileController;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.views.fragments.UserProfileFragment;

public class UserProfileActivity extends AppCompatActivity {
    public static final String USER = "USER";

    private UserResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (UserResponse) getIntent().getSerializableExtra(USER);
        getSupportActionBar().setTitle(user.getUsername());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_user_profile);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.user_profile_fragment);
        Log.d("Creating prof act", "frgment = " + fragment);

    }

    @Override
    public void onStart() {
        super.onStart();
        UserProfileController.getInstance().setUser(user);
        UserProfileController.getInstance().loadMore();
    }



}
