package com.dowrow.socialmedia.models;

import com.dowrow.socialmedia.models.entities.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.Call;

public interface SocialMediaService {

    @GET("me/?format=json")
    Call<UserResponse> getMe();

    @DELETE("me/?format=json")
    Call<ResponseBody> deleteMe();

}
