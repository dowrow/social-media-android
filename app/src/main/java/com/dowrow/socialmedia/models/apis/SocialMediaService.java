package com.dowrow.socialmedia.models.apis;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SocialMediaService {

    @GET("users/self/")
    Call<UserResponse> getSelf();

    @DELETE("users/self/")
    Call<ResponseBody> deleteSelf();

    @Multipart
    @POST("publications/")
    Call<PublicationResponse> createPublication(@Part("text") RequestBody text, @Part MultipartBody.Part file);

    @GET("publications/")
    Call<PaginatedResponse<PublicationResponse>> getGlobalPublications(@Query("cursor") String cursor);
}
