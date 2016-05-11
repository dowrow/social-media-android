package com.dowrow.socialmedia.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.dowrow.socialmedia.models.SocialMediaAPI;
import com.dowrow.socialmedia.views.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAccountController {

    public void deleteAccount(final Activity activity) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SocialMediaAPI api = new SocialMediaAPI();
                        api.getService().deleteMe().enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Toast toast = Toast.makeText(activity, "Your account has been deleted forever.", Toast.LENGTH_LONG);
                                    toast.show();
                                    LoginController.getInstance().logOut(activity);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast toast = Toast.makeText(activity, "The server failed to respond.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure?")
                .setPositiveButton("Delete account forever", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener)
                .show();
    }
}
