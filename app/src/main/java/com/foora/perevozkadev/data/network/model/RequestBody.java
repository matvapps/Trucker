package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class RequestBody {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("transports")
    @Expose
    private List<Integer> transports = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getTransports() {
        return transports;
    }

    public void setTransports(List<Integer> transports) {
        this.transports = transports;
    }
}
