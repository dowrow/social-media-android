package com.dowrow.socialmedia.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.views.adapters.MainActivityFragmentPagerAdapter;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_FILE = "IMAGE_FILE";

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new MainActivityFragmentPagerAdapter(getSupportFragmentManager(), this));
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setPublishCallback();

    }

    public void hideFAB() {
        fab.setVisibility(View.INVISIBLE);
    }
    public void showFAB() {
        fab.setVisibility(View.VISIBLE);
    }

    private void setPublishCallback() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        final Activity thisActivity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Upload picture")
                        .setItems(R.array.menu_publish_picture, new DialogInterface
                                .OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "The selected image can't be published.", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                onPhotoReturned(imageFile);
            }
        });
    }

    private void onPhotoReturned(File imageFile) {
        Intent intent = new Intent(getApplicationContext(), PublishActivity.class);
        intent.putExtra(IMAGE_FILE, imageFile);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Main activity", "destroyed");
    }
}
