package com.dowrow.socialmedia.views.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.views.viewholders.UserSearchViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserResponse> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.search_user_viewholder, viewGroup, false);
        return new UserSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserSearchViewHolder viewHolder = (UserSearchViewHolder) holder;
        viewHolder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAll(List<UserResponse> users) {
        int positionStart = this.items.size();
        int itemCount = users.size();
        this.items.addAll(users);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void add(UserResponse item) {
        this.items.add(item);
        notifyItemInserted(this.items.size() - 1);
    }

    public void remove(Object item) {
        Log.d("remove", "item = " + item);
        Log.d("remove", "items = " + items);
        Log.d("remove", "count = " + items.size());
        int position = items.indexOf(item);
        Log.d("remove", "position = " + position);
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