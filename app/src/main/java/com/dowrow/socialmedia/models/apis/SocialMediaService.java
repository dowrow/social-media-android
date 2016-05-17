package com.dowrow.socialmedia.models.apis;

import com.dowrow.socialmedia.models.entities.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SocialMediaService {

    @GET("me/?format=json")
    Call<UserResponse> getMe();

    @DELETE("me/?format=json")
    Call<ResponseBody> deleteMe();

    @Multipart
    @POST("publications/?format=json")
    Call<ResponseBody> createPublication(@Part("text") RequestBody text, @Part MultipartBody.Part file);
}
