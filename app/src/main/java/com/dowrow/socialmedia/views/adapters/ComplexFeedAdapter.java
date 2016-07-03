package com.dowrow.socialmedia.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.accounts.LoginController;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.views.viewholders.PublicationViewHolder;
import com.dowrow.socialmedia.views.viewholders.SelfProfileHeaderViewHolder;
import com.dowrow.socialmedia.views.viewholders.SelfPublicationViewHolder;
import com.dowrow.socialmedia.views.viewholders.UserProfileHeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ComplexFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items;

    private final int PUBLICATION = 0;

    private final int SELF_PUBLICATION = 1;

    private final int SELF_USER = 2;

    private final int USER = 3;

    public ComplexFeedAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof PublicationResponse) {
            PublicationResponse publicationResponse = (PublicationResponse) item;
            UserResponse self = LoginController.getInstance().getSelf();
            if (publicationResponse.getAuthorDetails().getId() == self.getId()) {
                return SELF_PUBLICATION;
            }
            return PUBLICATION;
        } else if (item instanceof UserResponse) {
            UserResponse user = (UserResponse) item;
            if (user.getId().equals(LoginController.getInstance().getSelf().getId())) {
                return SELF_USER;
            } else {
                return USER;
            }

        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {

            case SELF_PUBLICATION:
                View selfPublicationView = (View) inflater.inflate(R.layout.self_publication_viewholder, viewGroup, false);
                return new SelfPublicationViewHolder(selfPublicationView);

            case PUBLICATION:
                View publicationView = (View) inflater.inflate(R.layout.publication_viewholder, viewGroup, false);
                return new PublicationViewHolder(publicationView);

            case SELF_USER:
                View selfProfileView = (View) inflater.inflate(R.layout.self_profile_viewholder, viewGroup, false);
                return new SelfProfileHeaderViewHolder(selfProfileView);

            case USER:
                View userProfileView = (View) inflater.inflate(R.layout.user_profile_viewholder, viewGroup, false);
                return new UserProfileHeaderViewHolder(userProfileView);

        }
        Log.d("onCreateViewHolder", "undefined item type");
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {

            case SELF_PUBLICATION:
                SelfPublicationViewHolder selfPublicationViewHolder = (SelfPublicationViewHolder) viewHolder;
                selfPublicationViewHolder.bind((PublicationResponse) items.get(position));
                break;

            case PUBLICATION:
                PublicationViewHolder publicationViewHolder = (PublicationViewHolder) viewHolder;
                publicationViewHolder.bind((PublicationResponse) items.get(position));
                break;

            case SELF_USER:
                SelfProfileHeaderViewHolder selfProfileHeaderViewHolder = (SelfProfileHeaderViewHolder) viewHolder;
                selfProfileHeaderViewHolder.bind((UserResponse) items.get(position));
                break;

            case USER:
                UserProfileHeaderViewHolder userProfileHeaderViewHolder = (UserProfileHeaderViewHolder) viewHolder;
                userProfileHeaderViewHolder.bind((UserResponse) items.get(position));
                break;
        }
    }

    public void addAll(List<PublicationResponse> publications) {
        int positionStart = this.items.size();
        int itemCount = publications.size();
        this.items.addAll(publications);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void add(Object item) {
        this.items.add(item);
        notifyItemInserted(this.items.size() - 1);
    }

    public void remove(Object item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
