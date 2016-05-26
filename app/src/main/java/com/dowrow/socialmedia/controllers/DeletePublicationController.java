package com.dowrow.socialmedia.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
                switch (which){
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
                                        } catch (Exception e) {
                                            Log.d("Delete publication", e.getMessage());

                                        }
                                        Toast toast = Toast.makeText(view.getContext(), "Deleted!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast toast = Toast.makeText(view.getContext(), "Could not delete.", Toast.LENGTH_SHORT);
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
        builder.setMessage("Are you sure?")
                .setPositiveButton("Delete publication forever", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener)
                .show();

    }
}
