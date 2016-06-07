package com.dowrow.socialmedia.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FollowResponse {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("follower")
    @Expose
    private Integer follower;

    @SerializedName("followed")
    @Expose
    private Integer followed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }

}