
package com.dowrow.socialmedia.models.entities;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     * The authorDetails
     */
    public UserResponse getAuthorDetails() {
        return authorDetails;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Text: " + text + " Image: " + image;
    }

}