package com.dowrow.socialmedia.views.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.views.transformations.CropSquareTransformation;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PublicationResponseAdapter extends RecyclerView.Adapter<PublicationResponseAdapter.ViewHolder> {

    private List<PublicationResponse> publications;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View publicationView;
        public ViewHolder(View v) {
            super(v);
            publicationView = v;
        }
    }

    public PublicationResponseAdapter(List<PublicationResponse> myDataset) {
        publications = myDataset;
    }

    @Override
    public PublicationResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.publication_feed_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PublicationResponse publicationResponse = publications.get(position);
        ImageView profilePictureView = (ImageView) holder.publicationView
                .findViewById(R.id.publication_profile_picture);
        ImageView imageView = (ImageView) holder.publicationView
                .findViewById(R.id.publication_image);
        TextView usernameView = (TextView) holder.publicationView.findViewById(R.id.publication_username);
        TextView timeAgoView = (TextView) holder.publicationView.findViewById(R.id.publication_time_ago);
        TextView textView = (TextView) holder.publicationView.findViewById(R.id.publication_text);
        Context context = holder.publicationView.getContext();
        Picasso.with(context)
                .load(publicationResponse.getAuthorDetails().getProfilePicture())
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(new ColorDrawable(holder.publicationView.getResources()
                        .getColor(R.color.lightGray)))
                .transform(new CropSquareTransformation())
                .into(profilePictureView);
        Picasso.with(context)
                .load(publicationResponse.getImage())
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(new ColorDrawable(holder.publicationView.getResources()
                        .getColor(R.color.lightGray)))
                .transform(new CropSquareTransformation())
                .into(imageView);
        usernameView.setText(publicationResponse.getAuthorDetails().getUsername());
        timeAgoView.setText(getTimeAgo(publicationResponse.getTimestamp()));
        textView.setText(publicationResponse.getText());
    }

    private String getTimeAgo(String timestamp) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        try {
            inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Long datetime = inFormat.parse(timestamp).getTime();
            Long now = new Date().getTime();
            return DateUtils.getRelativeTimeSpanString(datetime, now, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (Exception e) {
            return "Long time ago";
        }
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

}