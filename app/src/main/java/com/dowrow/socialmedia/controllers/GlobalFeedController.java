package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.views.fragments.GlobalFeedFragment;

import retrofit2.Call;

public class GlobalFeedController extends AbstractFeedController {

    private static GlobalFeedController instance = null;

    public static GlobalFeedController getInstance(Fragment fragment) {
        instance = new GlobalFeedController(fragment);
        return instance;
    }

    public static GlobalFeedController getInstance() {
        return instance;
    }

    private GlobalFeedController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
       return api.getService().getGlobalPublications(nextCursor);
    }

}
