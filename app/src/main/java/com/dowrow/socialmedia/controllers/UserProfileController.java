package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;

import retrofit2.Call;

public class UserProfileController extends AbstractFeedController {

    private static UserProfileController instance = null;

    private UserResponse user;

    private UserProfileController(Fragment fragment) {
        super(fragment);
    }

    public static UserProfileController getInstance(Fragment fragment) {
        instance = new UserProfileController(fragment);
        return instance;
    }

    public static UserProfileController getInstance() {
        return instance;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    @Override
    public Call<UserResponse> getUserHeaderRequest() {
        return api.getService().getUser(user.getId().toString());
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
        return api.getService().getUserPublications(user.getId().toString(), nextCursor);
    }
}
