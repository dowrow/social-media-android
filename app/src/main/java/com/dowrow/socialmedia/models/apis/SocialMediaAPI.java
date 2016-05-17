package com.dowrow.socialmedia.models.apis;

import com.dowrow.socialmedia.controllers.LoginController;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SocialMediaAPI {

    String BASE_URL = "https://social-media-server.herokuapp.com/api/v0/";

    public SocialMediaService getService() {
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", LoginController.getInstance().getAuthorizationHeader()).build();
                return chain.proceed(request);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(httpClient).build();
        return retrofit.create(SocialMediaService.class);
    }

}
