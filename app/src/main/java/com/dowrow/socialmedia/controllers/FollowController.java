package com.dowrow.socialmedia.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.apis.SocialMediaService;
import com.dowrow.socialmedia.models.entities.FollowResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowController {

    private final Context context;

    public FollowController(Context context) {
        this.context = context;
    }

    private void follow(String followedId) {
        SocialMediaService service = new SocialMediaAPI().getService();
        service.follow(followedId).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                Log.d("followed", "ok!" + response.code());
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                Log.d("followed", "error!" + t.getMessage());
            }
        });
    }

    private void unfollow(String followedId) {
        UserResponse self = LoginController.getInstance().getSelf();
        SocialMediaService service = new SocialMediaAPI().getService();
        service.unfollow(followedId, self.getId().toString()).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                Log.d("unfollowed", "ok!" + response.code());
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                Log.d("unfollowed", "error!" + t.getMessage());
            }
        });
    }

    public void toggleFollow(UserResponse user) {
        if (user.isFollowed()) {
            unfollow(user.getId().toString());
        } else {
            follow(user.getId().toString());
        }
        user.setFollowed(!user.isFollowed());
    }

    public void styleFollowButton(UserResponse user, Button followButton) {
        if (user.isFollowed()) {
            showUnfollow(followButton);
        } else {
            showFollow(followButton);
        }
    }

    private void showFollow(Button button) {
        button.setBackground(context.getResources().getDrawable(R.drawable.solid_button_shape));
        button.setTextColor(context.getResources().getColor(R.color.white));
        button.setText(R.string.follow);
    }

    private void showUnfollow(Button button) {
        button.setBackground(context.getResources().getDrawable(R.drawable.linear_button_shape));
        button.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button.setText(R.string.unfollow);
    }
}
