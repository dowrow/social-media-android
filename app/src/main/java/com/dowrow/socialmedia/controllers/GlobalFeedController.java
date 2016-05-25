package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;

import retrofit2.Call;

public class GlobalFeedController extends AbstractFeedController {

    public GlobalFeedController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
       return api.getService().getGlobalPublications(nextCursor);
    }

}
