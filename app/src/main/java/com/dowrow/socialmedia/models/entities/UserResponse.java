package com.dowrow.socialmedia.models.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;

    @SerializedName("followed")
    @Expose
    private boolean followed;

    @SerializedName("publications_count")
    @Expose
    private int publicationsCount;

    @SerializedName("followers_count")
    @Expose
    private int followersCount;

    @SerializedName("following_count")
    @Expose
    private int followingCount;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public int getPublicationsCount() {
        return publicationsCount;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}
