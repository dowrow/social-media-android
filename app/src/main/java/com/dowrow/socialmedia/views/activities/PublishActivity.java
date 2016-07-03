package com.dowrow.socialmedia.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.PublishController;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PublishActivity extends AppCompatActivity {

    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.publication_preview_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_publish);
        imageFile = (File) getIntent().getExtras().get("IMAGE_FILE");
        ImageView imageView = (ImageView) findViewById(R.id.publish_imageView);
        Picasso.with(getApplication()).load(imageFile).fit().centerCrop().into(imageView);
    }

    public void publish(View v) {
        String text = ((EditText) findViewById(R.id.publishEditText)).getText().toString();
        if (text == null || text.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.you_must_add_title))
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            return;
        }
        finish();
        PublishController publishController = new PublishController();
        publishController.publish(this, imageFile, text);
    }

}
