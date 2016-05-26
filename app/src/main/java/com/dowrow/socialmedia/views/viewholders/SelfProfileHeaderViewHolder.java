package com.dowrow.socialmedia.views.viewholders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.DeleteAccountController;
import com.dowrow.socialmedia.controllers.LoginController;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class SelfProfileHeaderViewHolder extends RecyclerView.ViewHolder {

    private View view;

    private ImageView profilePicture;

    private TextView username;

    private TextView stats;

    private DeleteAccountController deleteAccountController;

    public SelfProfileHeaderViewHolder(View v) {
        super(v);
        view = v;
        profilePicture = (ImageView) v.findViewById(R.id.self_profile_picture);
        username = (TextView) v.findViewById(R.id.self_profile_username);
        stats = (TextView) v.findViewById(R.id.self_profile_stats);
        deleteAccountController = new DeleteAccountController();
    }

    public void bind(UserResponse user) {
        final Context context = view.getContext();
        Transformation circleTransformation = new RoundedTransformationBuilder()
                .borderColor(view.getResources().getColor(R.color.colorAccent))
                .borderWidthDp(1)
                .cornerRadiusDp(50)
                .oval(false)
                .build();
        Picasso.with(context).load(user.getProfilePicture()).transform(circleTransformation).into(profilePicture);
        username.setText(user.getUsername());
        stats.setText(user.getPublicationsCount() + " publications");
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
