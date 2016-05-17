package com.dowrow.socialmedia.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.DeleteAccountController;
import com.dowrow.socialmedia.controllers.LoginController;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class GlobalFeedActivity extends AppCompatActivity {

    private static final String IMAGE_FILE = "IMAGE_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Activity thisActivity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Upload picture")
                        .setItems(R.array.menu_publish_picture, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        EasyImage.openCamera(thisActivity, 0);
                                        break;
                                    case 1:
                                        EasyImage.openGallery(thisActivity, 0);
                                        break;
                                }
                            }
                        })
                        .setCancelable(true)
                        .show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_logout_item:
                LoginController.getInstance().logOut(this);
                return true;
            case R.id.menu_main_delete_account_item:
                DeleteAccountController deleteAccountController = new DeleteAccountController();
                deleteAccountController.deleteAccount(this);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                onPhotoReturned(imageFile);
            }


        });
    }

    private void onPhotoReturned(File imageFile) {
        Intent intent = new Intent(getApplicationContext(), PublishActivity.class);
        intent.putExtra(IMAGE_FILE, imageFile);
        startActivity(intent);

    }
}
