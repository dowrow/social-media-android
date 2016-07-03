package com.dowrow.socialmedia.models.apis;

import com.dowrow.socialmedia.models.entities.FollowResponse;
import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SocialMediaService {

    @GET("users/self/")
    Call<UserResponse> getSelf();

    @GET("users/{id}/")
    Call<UserResponse> getUser(@Path("id") String id);

    @DELETE("users/self/")
    Call<ResponseBody> deleteSelf();

    @Multipart
    @POST("publications/")
    Call<PublicationResponse> createPublication(@Part("text") RequestBody text, @Part MultipartBody.Part file);

    @GET("publications/")
    Call<PaginatedResponse<PublicationResponse>> getGlobalPublications(@Query("cursor") String cursor);

    @GET("publications/home/")
    Call<PaginatedResponse<PublicationResponse>> getHomePublications(@Query("cursor") String nextCursor);

    @DELETE("publications/{id}/")
    Call<ResponseBody> deletePublication(@Path("id") String publicationId);

    @GET("users/self/publications/")
    Call<PaginatedResponse<PublicationResponse>> getSelfPublications(@Query("cursor") String cursor);

    @GET("users/{id}/publications/")
    Call<PaginatedResponse<PublicationResponse>> getUserPublications(@Path("id") String id, @Query("cursor") String cursor);

    @GET("users/")
    Call<PaginatedResponse<UserResponse>> searchUsers(@Query("search") String query, @Query("cursor") String nextCursor);

    @POST("users/{id}/followers/")
    Call<FollowResponse> follow(@Path("id") String userId);

    @DELETE("users/{followed_id}/followers/{follower_id}/")
    Call<FollowResponse> unfollow(@Path("followed_id") String followerUserId,
                                  @Path("follower_id") String followedUserId);

}
