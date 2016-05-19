package com.dowrow.socialmedia.controllers;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.exceptions.NoMorePagesException;
import com.dowrow.socialmedia.views.adapters.PublicationResponseAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalFeedController {

    SocialMediaAPI api;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private PublicationResponseAdapter adapter;

    private LinearLayoutManager layoutManager;

    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    private String nextCursor = "";

    private Activity activity;

    public GlobalFeedController(Activity globalFeedActivity) {
        activity = globalFeedActivity;
        api = new SocialMediaAPI();
        recyclerView = (RecyclerView) activity.findViewById(R.id.publication_feed);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.publication_feed_swipe);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PublicationResponseAdapter();
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
        adapter.clear();
        loadMore();
        endlessRecyclerViewScrollListener.reset();
    }

    public void loadMore() {
        api.getService().getGlobalPublications(nextCursor)
                .enqueue(new Callback<PaginatedResponse<PublicationResponse>>() {

                    @Override
                    public void onResponse(Call<PaginatedResponse<PublicationResponse>> call,
                                           Response<PaginatedResponse<PublicationResponse>> response) {
                        if (nextCursor == null) {
                            Toast toast = Toast.makeText(activity,
                                    "There's no more publications", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        adapter.addAll(response.body().getResults());
                        try {
                            nextCursor = response.body().getCursorNext();
                        } catch (NoMorePagesException e) {
                            nextCursor = null;
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<PaginatedResponse<PublicationResponse>> call, Throwable t) {
                        Log.d("GlobalPubs error", t.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                });
    }
}
