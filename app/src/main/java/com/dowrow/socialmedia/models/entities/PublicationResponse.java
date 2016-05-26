
package com.dowrow.socialmedia.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PublicationResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("author_details")
    @Expose
    private UserResponse authorDetails;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("text")
    @Expose
    private String text;

    public Integer getId() {
        return id;
    }

    public UserResponse getAuthorDetails() {
        return authorDetails;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Text: " + text + " Image: " + image;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PublicationResponse)) {
            return false;
        }
        return this.id == ((PublicationResponse) obj).getId();
    }
}