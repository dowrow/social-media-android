package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfProfileController extends AbstractFeedController {

    private static SelfProfileController instance = null;

    public static SelfProfileController getInstance(Fragment fragment) {
        instance = new SelfProfileController(fragment);
        return instance;
    }

    public static SelfProfileController getInstance() {
        return instance;
    }

    private SelfProfileController(Fragment fragment) {
        super(fragment);
        loadSelfProfile();
    }

    private void loadSelfProfile() {
        api.getService().getSelf().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response != null) {
                    adapter.setSelfProfileHeader(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
        return api.getService().getSelfPublications(nextCursor);
    }

}
