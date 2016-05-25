package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;

import retrofit2.Call;

public class SelfProfileController extends AbstractFeedController {

    public SelfProfileController(Fragment fragment) {
        super(fragment);
        adapter.setSelfProfileHeader(LoginController.getInstance().getSelf());
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
        return api.getService().getSelfPublications(nextCursor);
    }

}
