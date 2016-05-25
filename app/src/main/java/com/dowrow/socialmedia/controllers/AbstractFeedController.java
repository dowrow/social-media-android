package com.dowrow.socialmedia.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.exceptions.NoMorePagesException;
import com.dowrow.socialmedia.views.adapters.ComplexFeedAdapter;
import com.dowrow.socialmedia.views.adapters.EndlessRecyclerViewScrollListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractFeedController {

    protected SocialMediaAPI api;

    protected String nextCursor = "";

    protected Fragment mFragment;

    protected ComplexFeedAdapter adapter;

    private RecyclerView recyclerView;

    protected SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager layoutManager;

    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    public AbstractFeedController(Fragment fragment) {
        mFragment = fragment;
        api = new SocialMediaAPI();
        recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.publication_feed);
        swipeRefreshLayout = (SwipeRefreshLayout) fragment.getView().findViewById(R.id.publication_feed_swipe);
        layoutManager = new LinearLayoutManager(fragment.getView().getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComplexFeedAdapter();
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
        getLoadMoreRequest(nextCursor).enqueue(new Callback<PaginatedResponse<PublicationResponse>>() {
            @Override
            public void onResponse(Call<PaginatedResponse<PublicationResponse>> call,
                                   Response<PaginatedResponse<PublicationResponse>> response) {
                if (nextCursor == null) {
                    return;
                }
                try {
                    adapter.addAll(response.body().getResults());
                    nextCursor = response.body().getCursorNext();
                } catch (NoMorePagesException e) {
                    nextCursor = null;
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PaginatedResponse<PublicationResponse>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public abstract Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor);

}
