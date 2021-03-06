package com.dowrow.socialmedia.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.feeds.GlobalFeedController;

public class GlobalFeedFragment extends Fragment {

    private GlobalFeedController globalFeedController;

    public GlobalFeedFragment() {
    }

    public static GlobalFeedFragment newInstance() {
        return new GlobalFeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.infinite_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        globalFeedController = GlobalFeedController.getInstance(this);
        globalFeedController.loadMore();
    }
}
