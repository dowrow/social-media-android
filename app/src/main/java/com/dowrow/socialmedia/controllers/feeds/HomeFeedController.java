package com.dowrow.socialmedia.controllers.feeds;

import android.support.v4.app.Fragment;

import com.dowrow.socialmedia.models.entities.PaginatedResponse;
import com.dowrow.socialmedia.models.entities.PublicationResponse;
import com.dowrow.socialmedia.models.entities.UserResponse;

import retrofit2.Call;

public class HomeFeedController extends AbstractFeedController {

    private static HomeFeedController instance = null;

    public static HomeFeedController getInstance(Fragment fragment) {
        instance = new HomeFeedController(fragment);
        return instance;
    }

    public static HomeFeedController getInstance() {
        return instance;
    }

    private HomeFeedController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Call<UserResponse> getUserHeaderRequest() {
        return null;
    }

    @Override
    public Call<PaginatedResponse<PublicationResponse>> getLoadMoreRequest(String nextCursor) {
        return api.getService().getHomePublications(nextCursor);
    }

}
