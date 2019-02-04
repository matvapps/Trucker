package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander Matvienko on 04.02.2019.
 */
public class Action {
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("action")
    @Expose
    private String action;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Action{" +
                "time='" + time + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
