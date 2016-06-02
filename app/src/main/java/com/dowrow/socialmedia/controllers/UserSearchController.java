package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;

import retrofit2.Call;

public class UserSearchController extends AbstractUserFeedController{

    private static UserSearchController instance = null;

    private String query = "";

    public static UserSearchController getInstance(Fragment fragment) {
        instance = new UserSearchController(fragment);
        return instance;
    }

    private UserSearchController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Call<UserResponse> getUserHeaderRequest() {
        return null;
    }

    @Override
    public Call<PaginatedResponse<UserResponse>> getLoadMoreRequest(String nextCursor) {
        return api.getService().searchUsers(getQuery(), nextCursor);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
