package com.dowrow.socialmedia.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.PublishController;
import com.dowrow.socialmedia.views.transformations.CropSquareTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PublishActivity extends AppCompatActivity {

    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add title");
        setContentView(R.layout.activity_publish);
        imageFile = (File) getIntent().getExtras().get("IMAGE_FILE");
        ImageView imageView =  (ImageView) findViewById(R.id.publish_imageView);
        Picasso.with(getApplication()).load(imageFile).transform(new CropSquareTransformation())
                .into(imageView);
    }

    public void publish(View v) {
        finish();
        PublishController publishController = new PublishController();
        String text = ((EditText) findViewById(R.id.publishEditText)).getText().toString();
        publishController.publish(this, imageFile, text);
    }
}
