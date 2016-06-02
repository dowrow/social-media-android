package com.dowrow.socialmedia.views.fragments;

import android.support.v4.app.Fragment;

public class HomeFeedFragment extends Fragment {

    private static HomeFeedFragment instance = null;

    public static HomeFeedFragment newInstance() {
        if (instance == null) {
            instance = new HomeFeedFragment();
        }
        return instance;
    }

    public HomeFeedFragment() {

    }



}
