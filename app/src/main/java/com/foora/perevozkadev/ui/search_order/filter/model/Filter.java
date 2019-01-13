package com.foora.perevozkadev.ui.search_order.filter.model;

import com.foora.perevozkadev.ui.add_order.model.Place;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class Filter implements Serializable {

    @SerializedName("loading_places")
    @Expose
    private List<Place> loadingPlaces = null;
    @SerializedName("unloading_places")
    @Expose
    private List<Place> unloadingPlaces = null;
    @Expose
    private String loadingDate;
    @SerializedName("unloading_date")
    @Expose
    private String unloadingDate;
    @SerializedName("weight_from")
    @Expose
    private float weightFrom;
    @SerializedName("weight_to")
    @Expose
    private float weightTo;
    @SerializedName("volume_from")
    @Expose
    private float volumeFrom;
    @SerializedName("volume_to")
    @Expose
    private float volumeTo;
    @SerializedName("transport_type")
    @Expose
    private String transportType;

    public Filter() {
    }

    public Filter(List<Place> loadingPlaces, List<Place> unloadingPlaces, String loadingDate,
                  String unloadingDate, float weightFrom, float weightTo,
                  float volumeFrom, float volumeTo, String transportType) {
        this.loadingPlaces = loadingPlaces;
        this.unloadingPlaces = unloadingPlaces;
        this.loadingDate = loadingDate;
        this.unloadingDate = unloadingDate;
        this.weightFrom = weightFrom;
        this.weightTo = weightTo;
        this.volumeFrom = volumeFrom;
        this.volumeTo = volumeTo;
        this.transportType = transportType;
    }

    public List<Place> getLoadingPlaces() {
        return loadingPlaces;
    }

    public void setLoadingPlaces(List<Place> loadingPlaces) {
        this.loadingPlaces = loadingPlaces;
    }

    public List<Place> getUnloadingPlaces() {
        return unloadingPlaces;
    }

    public void setUnloadingPlaces(List<Place> unloadingPlaces) {
        this.unloadingPlaces = unloadingPlaces;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public String getUnloadingDate() {
        return unloadingDate;
    }

    public void setUnloadingDate(String unloadingDate) {
        this.unloadingDate = unloadingDate;
    }

    public float getWeightFrom() {
        return weightFrom;
    }

    public void setWeightFrom(float weightFrom) {
        this.weightFrom = weightFrom;
    }

    public float getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(float weightTo) {
        this.weightTo = weightTo;
    }

    public float getVolumeFrom() {
        return volumeFrom;
    }

    public void setVolumeFrom(float volumeFrom) {
        this.volumeFrom = volumeFrom;
    }

    public float getVolumeTo() {
        return volumeTo;
    }

    public void setVolumeTo(float volumeTo) {
        this.volumeTo = volumeTo;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "loadingPlaces=" + loadingPlaces +
                ", unloadingPlaces=" + unloadingPlaces +
                ", loadingDate='" + loadingDate + '\'' +
                ", unloadingDate='" + unloadingDate + '\'' +
                ", weightFrom=" + weightFrom +
                ", weightTo=" + weightTo +
                ", volumeFrom=" + volumeFrom +
                ", volumeTo=" + volumeTo +
                ", transportType='" + transportType + '\'' +
                '}';
    }


}
