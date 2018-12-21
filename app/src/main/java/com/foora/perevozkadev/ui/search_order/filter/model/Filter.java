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
    private Double weightFrom;
    @SerializedName("weight_to")
    @Expose
    private Double weightTo;
    @SerializedName("volume_from")
    @Expose
    private Double volumeFrom;
    @SerializedName("volume_to")
    @Expose
    private Double volumeTo;
    @SerializedName("transport_type")
    @Expose
    private String transportType;

    public Filter() {
    }

    public Filter(List<Place> loadingPlaces, List<Place> unloadingPlaces, String loadingDate,
                  String unloadingDate, Double weightFrom, Double weightTo,
                  Double volumeFrom, Double volumeTo, String transportType) {
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
