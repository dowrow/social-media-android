package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;
import com.dowrow.socialmedia.models.exceptions.NoMorePagesException;
import com.dowrow.socialmedia.views.adapters.EndlessRecyclerViewScrollListener;
import com.dowrow.socialmedia.views.adapters.UserFeedAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractUserFeedController {

        protected SocialMediaAPI api;

        protected String nextCursor = "";

        protected Fragment mFragment;

        protected UserFeedAdapter adapter;

        private RecyclerView recyclerView;

        protected SwipeRefreshLayout swipeRefreshLayout;

        private LinearLayoutManager layoutManager;

        private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

        public AbstractUserFeedController(Fragment fragment) {
            mFragment = fragment;
            api = new SocialMediaAPI();
            recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.publication_feed);
            swipeRefreshLayout = (SwipeRefreshLayout) fragment.getView().findViewById(R.id.publication_feed_swipe);
            layoutManager = new LinearLayoutManager(fragment.getView().getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new UserFeedAdapter();
            endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore() {
                    loadMore();
                }
            };
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
        }

        public void refresh() {
            nextCursor = "";
            loadMore();
            endlessRecyclerViewScrollListener.reset();
        }

        public void loadMore() {
            Call<UserResponse> userHeaderRequest = getUserHeaderRequest();
            if (userHeaderRequest != null && adapter.isEmpty()) {
                userHeaderRequest.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        adapter.clear();
                        adapter.add(response.body());
                        loadUsers();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        loadUsers();
                    }
                });
            } else {
                loadUsers();
            }
        }

        public void loadUsers() {
            getLoadMoreRequest(nextCursor).enqueue(new Callback<PaginatedResponse<UserResponse>>() {
                @Override
                public void onResponse(Call<PaginatedResponse<UserResponse>> call,
                                       Response<PaginatedResponse<UserResponse>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mFragment.getContext(), "No results", Toast.LENGTH_SHORT).show();
                        Log.d("loadUsers", "response not successful");
                        Log.d("response", response.code() + "");
                    }

                    if (nextCursor == null) {
                        return;
                    }

                    if (response != null && response.body() != null) {
                        Log.d("loadUsers() success", response.body().getResults().toString());
                        adapter.clear();
                        adapter.addAll(response.body().getResults());
                        try {
                            nextCursor = response.body().getCursorNext();
                        } catch (NoMorePagesException e) {
                            nextCursor = null;
                        }
                    } else {
                        Log.d("loadUsers()", "null response");
                    }

                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<PaginatedResponse<UserResponse>> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }

        public void remove(Object item) {
            adapter.remove(item);
        }

        public abstract Call<UserResponse> getUserHeaderRequest();

        public abstract Call<PaginatedResponse<UserResponse>> getLoadMoreRequest(String nextCursor);

}
