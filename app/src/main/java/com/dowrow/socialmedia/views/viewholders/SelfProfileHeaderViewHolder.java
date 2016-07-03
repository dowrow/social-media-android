package com.dowrow.socialmedia.views.viewholders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.accounts.DeleteAccountController;
import com.dowrow.socialmedia.controllers.accounts.LoginController;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class SelfProfileHeaderViewHolder extends RecyclerView.ViewHolder {

    private View view;

    private ImageView profilePicture;

    private TextView username;

    private TextView publicationsCount;

    private TextView followersCount;

    private TextView followingCount;

    private DeleteAccountController deleteAccountController;

    public SelfProfileHeaderViewHolder(View v) {
        super(v);
        view = v;
        profilePicture = (ImageView) v.findViewById(R.id.self_profile_picture);
        username = (TextView) v.findViewById(R.id.self_profile_username);
        publicationsCount = (TextView) v.findViewById(R.id.self_profile_publication_count);
        followersCount = (TextView) v.findViewById(R.id.self_profile_followers_count);
        followingCount = (TextView) v.findViewById(R.id.self_profile_following_count);
        deleteAccountController = new DeleteAccountController();
    }

    public void bind(UserResponse user) {
        final Context context = view.getContext();
        Transformation circleTransformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(50)
                .oval(false)
                .build();
        Picasso.with(context).load(user.getProfilePicture()).transform(circleTransformation).into(profilePicture);
        username.setText(user.getUsername().toString());
        publicationsCount.setText(user.getPublicationsCount() + "");
        followingCount.setText(user.getFollowingCount() + "");
        followersCount.setText(user.getFollowersCount() + "");
        view.findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginController.getInstance().logOut((Activity) context);
            }
        });
        view.findViewById(R.id.delete_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountController.deleteAccount((Activity) context);
            }
        });
    }
}
