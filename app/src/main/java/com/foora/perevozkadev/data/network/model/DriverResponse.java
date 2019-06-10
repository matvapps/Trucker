package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class DriverResponse {

    @SerializedName("drivers")
    @Expose
    List<Integer> drivers;

    public DriverResponse() {
    }

    public List<Integer> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Integer> drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "DriverResponse{" +
                "drivers=" + drivers +
                '}';
    }
}
