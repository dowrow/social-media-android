package com.dowrow.socialmedia.views.viewholders;

import android.view.View;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.publications.DeletePublicationController;

public class SelfPublicationViewHolder extends PublicationViewHolder {

    private DeletePublicationController deletePublicationController;

    public SelfPublicationViewHolder(View v) {
        super(v);
        deletePublicationController = new DeletePublicationController();
        setDeleteCallback(v);
    }

    private void setDeleteCallback(View v) {
        v.findViewById(R.id.publication_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublicationController.deletePublication(publicationResponse, v);
            }
        });
    }

}
