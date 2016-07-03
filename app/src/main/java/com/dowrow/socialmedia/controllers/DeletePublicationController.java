package com.dowrow.socialmedia.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.feedcontrollers.GlobalFeedController;
import com.dowrow.socialmedia.controllers.feedcontrollers.HomeFeedController;
import com.dowrow.socialmedia.controllers.feedcontrollers.SelfProfileController;
import com.dowrow.socialmedia.models.apis.SocialMediaAPI;
import com.dowrow.socialmedia.models.entities.PublicationResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletePublicationController {

    public void deletePublication(final PublicationResponse publicationResponse, final View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        SocialMediaAPI api = new SocialMediaAPI();
                        api.getService().deletePublication(publicationResponse.getId().toString())
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            Log.d("Delete publication", "global");
                                            GlobalFeedController.getInstance().remove(publicationResponse);
                                            Log.d("Delete publication", "self");
                                            SelfProfileController.getInstance().remove(publicationResponse);
                                            Log.d("Delete publication", "home");
                                            HomeFeedController.getInstance().remove(publicationResponse);
                                        } catch (Exception e) {

                                        }
                                        Toast toast = Toast.makeText(view.getContext(), R.string.deleted_notification, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast toast = Toast.makeText(view.getContext(), R.string.could_not_delete, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.delete_publication_forever, dialogClickListener)
                .setNegativeButton(R.string.cancel, dialogClickListener)
                .show();

    }
}
