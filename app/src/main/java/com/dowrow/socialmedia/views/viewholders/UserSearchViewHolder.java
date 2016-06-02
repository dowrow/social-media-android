package com.dowrow.socialmedia.views.viewholders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.views.activities.UserProfileActivity;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.Serializable;

public class UserSearchViewHolder extends RecyclerView.ViewHolder {

    private View view;

    private UserResponse userResponse;

    private Button followButton;

    private ImageView profilePicture;

    private TextView username;

    public UserSearchViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        followButton = (Button) view.findViewById(R.id.search_user_follow_button);
        profilePicture = (ImageView) view.findViewById(R.id.search_user_profile_picture);
        username = (TextView) view.findViewById(R.id.search_user_username);
    }

    public void bind(final UserResponse userResponse) {
        this.userResponse = userResponse;
        Transformation circleTransformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(50)
                .oval(false)
                .build();
        if (userResponse.getProfilePicture().isEmpty()) {
            Picasso.with(view.getContext()).load(R.drawable.ic_add_user)
                    .transform(circleTransformation).into(profilePicture);
        } else {
            Picasso.with(view.getContext()).load(userResponse.getProfilePicture())
                    .transform(circleTransformation).into(profilePicture);
        }

        username.setText(userResponse.getUsername());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserProfile(userResponse);
            }
        });
        profilePicture
                .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            openUserProfile(userResponse);
                                        }
                                    }

        );
    }

    private void openUserProfile(UserResponse userResponse) {
        Intent intent = new Intent(view.getContext(), UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.USER, (Serializable) userResponse);
        view.getContext().startActivity(intent);
    }
}
