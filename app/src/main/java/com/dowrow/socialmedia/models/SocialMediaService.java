package com.dowrow.socialmedia.models;

import retrofit2.http.GET;
import retrofit2.Call;

public interface SocialMediaService {

    @GET("me/?format=json")
    Call<String> getMe();

}
