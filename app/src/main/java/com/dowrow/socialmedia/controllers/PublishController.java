package com.dowrow.socialmedia.controllers;

import android.app.Activity;
import android.widget.Toast;

import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.apis.SocialMediaService;
import com.dowrow.socialmedia.models.entities.PublicationResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishController {

    public void publish(final Activity publishActivity, File imageFile, String text) {
        SocialMediaAPI api = new SocialMediaAPI();
        SocialMediaService service = api.getService();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
        RequestBody requestText = RequestBody.create(MediaType.parse("multipart/form-data"), text);
        Call<PublicationResponse> call = service.createPublication(requestText, body);
        call.enqueue(new Callback<PublicationResponse>() {
            @Override
            public void onResponse(Call<PublicationResponse> call,
                                   Response<PublicationResponse> response) {
                if (response.code() == 201) {
                    Toast toast = Toast.makeText(publishActivity, "Published!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(publishActivity, "We couldn't publish your picture", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<PublicationResponse> call, Throwable t) {
                Toast toast = Toast.makeText(publishActivity, "Error publishing your picture", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


}
