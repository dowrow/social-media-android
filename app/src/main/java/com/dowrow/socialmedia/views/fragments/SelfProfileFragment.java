package com.dowrow.socialmedia.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.DeleteAccountController;
import com.dowrow.socialmedia.controllers.LoginController;
import com.dowrow.socialmedia.controllers.SelfProfileController;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.squareup.picasso.Picasso;

public class SelfProfileFragment extends Fragment {

    DeleteAccountController deleteAccountController;

    SelfProfileController selfProfileController;

    public static SelfProfileFragment newInstance() {
        return new SelfProfileFragment();
    }

    public SelfProfileFragment() {
        deleteAccountController = new DeleteAccountController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.publication_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        selfProfileController = new SelfProfileController(this);
        selfProfileController.loadMore();
    }


}
