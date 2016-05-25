package com.dowrow.socialmedia.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.dowrow.socialmedia.models.apis.SocialMediaAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletePublicationController {
    public void deletePublication(final String id, final View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SocialMediaAPI api = new SocialMediaAPI();
                        api.getService().deletePublication(id).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
