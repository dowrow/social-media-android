package com.dowrow.socialmedia.views.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.views.transformations.CropSquareTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GlobalFeedAdapter extends RecyclerView.Adapter<GlobalFeedAdapter.ViewHolder> {

    private List<PublicationResponse> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View publicationView;
        public ViewHolder(View v) {
            super(v);
            publicationView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GlobalFeedAdapter(List<PublicationResponse> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GlobalFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.publication_feed_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from your dataset at this position
        PublicationResponse publicationResponse = mDataset.get(position);
        // Get views
        ImageView profilePictureView = (ImageView) holder.publicationView
                .findViewById(R.id.publication_profile_picture);
        ImageView imageView = (ImageView) holder.publicationView
                .findViewById(R.id.publication_image);
        TextView usernameView = (TextView) holder.publicationView.findViewById(R.id.publication_username);
        TextView timeAgoView = (TextView) holder.publicationView.findViewById(R.id.publication_time_ago);
        TextView textView = (TextView) holder.publicationView.findViewById(R.id.publication_text);
        Context context = holder.publicationView.getContext();
        // Replace contents
        Picasso.with(context).load(publicationResponse.getAuthorDetails().getProfilePicture())
                .placeholder(new ColorDrawable(holder.publicationView.getResources()
                        .getColor(R.color.lightGray)))
                .transform(new CropSquareTransformation()).into(profilePictureView);
        Picasso.with(context).load(publicationResponse.getImage())
                .placeholder(new ColorDrawable(holder.publicationView.getResources()
                        .getColor(R.color.lightGray)))
                .transform(new CropSquareTransformation()).into(imageView);
        usernameView.setText(publicationResponse.getAuthorDetails().getUsername());
        timeAgoView.setText(getTimeAgo(publicationResponse.getTimestamp()));
        textView.setText(publicationResponse.getText());
    }

    private String getTimeAgo(String timestamp) {
        Log.d("timestamp", timestamp);
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");

        try {
            inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Long datetime = inFormat.parse(timestamp).getTime();
            Long now = new Date().getTime();
            return DateUtils.getRelativeTimeSpanString(datetime, now, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (Exception e) {
            Log.d("timestamp excp", e.getMessage());
            return "Long time ago";
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}