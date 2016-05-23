package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.exceptions.NoMorePagesException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfProfileController extends AbstractFeedController {

    public SelfProfileController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
        return api.getService().getSelfPublications(nextCursor);
    }

}
