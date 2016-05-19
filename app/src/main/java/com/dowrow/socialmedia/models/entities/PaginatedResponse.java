package com.dowrow.socialmedia.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PaginatedResponse<U> {

    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("results")
    @Expose
    private List<U> results = new ArrayList<U>();

    public String getCursorNext() {
        if (next == null) {
            return "";
        }
        return next.split("cursor=")[1];
    }

    public String getCursorPrevious() {
        if (previous == null) {
            return "";
        }
        return previous.split("cursor=")[1];
    }

    public List<U> getResults() {
        return results;
    }

}