package com.dowrow.socialmedia.controllers;

import android.app.Activity;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.feedcontrollers.GlobalFeedController;
import com.dowrow.socialmedia.controllers.feedcontrollers.HomeFeedController;
import com.dowrow.socialmedia.controllers.feedcontrollers.SelfProfileController;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.apis.SocialMediaService;
import com.dowrow.socialmedia.models.entities.PublicationResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                    try {
                        GlobalFeedController.getInstance().refresh();
                        SelfProfileController.getInstance().refresh();
                        HomeFeedController.getInstance().refresh();
                    } catch (Exception e) {

                    }

                    Toast toast = Toast.makeText(publishActivity, R.string.published, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(publishActivity, R.string.could_not_publish, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<PublicationResponse> call, Throwable t) {
                Toast toast = Toast.makeText(publishActivity, R.string.error_publishing, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


}
