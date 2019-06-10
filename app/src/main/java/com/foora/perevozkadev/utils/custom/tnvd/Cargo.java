package com.foora.perevozkadev.utils.custom.tnvd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class Cargo {

    @SerializedName("id")
    @Expose
    private long cargoId;
    @SerializedName("name")
    @Expose
    private String cargoName;

    public long getCargoId() {
        return cargoId;
    }

    public void setCargoId(long cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    @Override
    public String toString() {
        return cargoName;
    }
}
