package com.dowrow.socialmedia.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PaginatedResponse<U> {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("results")
    @Expose
    private List<U> results = new ArrayList<U>();

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @return The next
     */
    public String getNext() {
        return next;
    }


    /**
     * @return The previous
     */
    public String getPrevious() {
        return previous;
    }


    /**
     * @return The results
     */
    public List<U> getResults() {
        return results;
    }

}