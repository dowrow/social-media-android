package com.dowrow.socialmedia.views.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.views.activities.UserProfileActivity;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PublicationViewHolder extends RecyclerView.ViewHolder {

    View view;

    private ImageView profilePicture;

    private TextView username;

    private TextView timeAgo;

    private ImageView image;

    private TextView text;

    protected PublicationResponse publicationResponse;

    public PublicationViewHolder(View v) {
        super(v);
        view = v;
        profilePicture = (ImageView) v.findViewById(R.id.publication_profile_picture);
        username = (TextView) v.findViewById(R.id.publication_username);
        timeAgo = (TextView) v.findViewById(R.id.publication_time_ago);
        image = (ImageView) v.findViewById(R.id.publication_image);
        text = (TextView) v.findViewById(R.id.publication_text);
    }

    public void bind(PublicationResponse publicationResponse) {
        this.publicationResponse = publicationResponse;
        Context context = view.getContext();

        Transformation circleTransformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(50)
                .oval(false)
                .build();

        Picasso.with(context)
                .load(publicationResponse.getAuthorDetails().getProfilePicture())
                .placeholder(new ColorDrawable(view.getResources().getColor(R.color.lightGray)))
                .fit()
                .transform(circleTransformation)
                .into(profilePicture);

        Picasso.with(context)
                .load(publicationResponse.getImage())
                .placeholder(new ColorDrawable(view.getResources().getColor(R.color.lightGray)))
                .fit()
                .centerCrop()
                .into(image);

        username.setText(publicationResponse.getAuthorDetails().getUsername());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUserProfile();
            }
        });
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUserProfile();
            }
        });
        timeAgo.setText(getTimeAgo(publicationResponse.getTimestamp()));
        text.setText(publicationResponse.getText());
    }

    protected void viewUserProfile() {
        Intent intent = new Intent(view.getContext(), UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.USER, (Serializable) publicationResponse.getAuthorDetails());
        view.getContext().startActivity(intent);
    }

    private String getTimeAgo(String timestamp) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        try {
            inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Long datetime = inFormat.parse(timestamp).getTime();
            Long now = new Date().getTime();
            if (datetime > now) {
                return view.getContext().getString(R.string.just_now);
            }
            String relativeTimeSpan = DateUtils.getRelativeTimeSpanString(datetime, now, DateUtils.SECOND_IN_MILLIS).toString();
            return relativeTimeSpan.substring(0, 1).toUpperCase() + relativeTimeSpan.substring(1);
        } catch (Exception e) {
            return view.getContext().getString(R.string.long_time_ago);
        }
    }
}