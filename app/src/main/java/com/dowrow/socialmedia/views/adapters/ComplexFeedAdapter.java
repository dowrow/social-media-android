package com.dowrow.socialmedia.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.views.viewholders.PublicationViewHolder;
import com.dowrow.socialmedia.views.viewholders.SelfProfileHeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ComplexFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items;

    private final int PUBLICATION = 0;

    private final int USER = 1;

    private UserResponse userResponse = null;

    private boolean usingUserSelfHeader = false;

    public ComplexFeedAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof PublicationResponse) {
            return PUBLICATION;
        } else if (items.get(position) instanceof UserResponse) {
            return USER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {

            case PUBLICATION:

                View publicationView = (View) inflater.inflate(R.layout.publication_viewholder, viewGroup, false);
                return new PublicationViewHolder(publicationView);

            case USER:
                if (usingUserSelfHeader) {
                    View selfProfileView = (View) inflater.inflate(R.layout.self_profile_viewholder, viewGroup, false);
                    return new SelfProfileHeaderViewHolder(selfProfileView);
                }
                return null;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case PUBLICATION:
                PublicationViewHolder publicationViewHolder = (PublicationViewHolder) viewHolder;
                publicationViewHolder.bind((PublicationResponse)items.get(position));
                break;
            case USER:
                if (usingUserSelfHeader){
                    SelfProfileHeaderViewHolder selfProfileHeaderViewHolder = (SelfProfileHeaderViewHolder) viewHolder;
                    selfProfileHeaderViewHolder.bind(userResponse);
                }
                break;
        }
    }

    public void setSelfProfileHeader(UserResponse self) {
        usingUserSelfHeader = true;
        this.userResponse = self;
        items.add(0, self);
    }

    public void addAll(List<PublicationResponse> publications) {
        int positionStart = this.items.size();
        int itemCount = publications.size();
        this.items.addAll(publications);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void clear() {
        this.items.clear();
        if (usingUserSelfHeader) {
            items.add(userResponse);
        }
        notifyDataSetChanged();
    }
}
