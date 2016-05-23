package com.dowrow.socialmedia.models.entities;

import com.dowrow.socialmedia.models.exceptions.NoMorePagesException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public String getCursorNext() throws NoMorePagesException {
        if (next == null) {
            throw new NoMorePagesException();
        }
        return next.split("cursor=")[1];
    }

    public String getCursorPrevious() throws NoMorePagesException {
        if (previous == null) {
            throw new NoMorePagesException();
        }
        return previous.split("cursor=")[1];
    }

    public List<U> getResults() {
        return results;
    }

}