package com.dowrow.socialmedia.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.DeleteAccountController;
import com.dowrow.socialmedia.controllers.feedcontrollers.SelfProfileController;

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
        return inflater.inflate(R.layout.infinite_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view, bundle);
        selfProfileController = SelfProfileController.getInstance(this);
        selfProfileController.loadMore();
    }


}
