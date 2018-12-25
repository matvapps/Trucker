package com.foora.perevozkadev.ui.my_transport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Matvienko on 21.12.2018.
 */
public class Transport implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("registration_num")
    @Expose
    private String registrationNum;
    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("passport_num")
    @Expose
    private String passportNum;
    @SerializedName("allowed_weight")
    @Expose
    private float allowedWeight;
    @SerializedName("no_load_mass")
    @Expose
    private float noLoadMass;
    @SerializedName("registration_place")
    @Expose
    private String registrationPlace;
    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("vehicle_condition")
    @Expose
    private String vehicleCondition;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Transport() {
    }

    public Transport(String registrationNum, String vin, String model, String category,
                     String type, String passportNum, Integer allowedWeight,
                     Integer noLoadMass, String registrationPlace, String registrationDate,
                     String location, String vehicleCondition) {
        this.registrationNum = registrationNum;
        this.vin = vin;
        this.model = model;
        this.category = category;
        this.type = type;
        this.passportNum = passportNum;
        this.allowedWeight = allowedWeight;
        this.noLoadMass = noLoadMass;
        this.registrationPlace = registrationPlace;
        this.registrationDate = registrationDate;
        this.location = location;
        this.vehicleCondition = vehicleCondition;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public float getAllowedWeight() {
        return allowedWeight;
    }

    public void setAllowedWeight(float allowedWeight) {
        this.allowedWeight = allowedWeight;
    }

    public float getNoLoadMass() {
        return noLoadMass;
    }

    public void setNoLoadMass(float noLoadMass) {
        this.noLoadMass = noLoadMass;
    }

    public String getRegistrationPlace() {
        return registrationPlace;
    }

    public void setRegistrationPlace(String registrationPlace) {
        this.registrationPlace = registrationPlace;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(String vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", user=" + user +
                ", photos=" + photos +
                ", registrationNum='" + registrationNum + '\'' +
                ", vin='" + vin + '\'' +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", passportNum='" + passportNum + '\'' +
                ", allowedWeight=" + allowedWeight +
                ", noLoadMass=" + noLoadMass +
                ", registrationPlace='" + registrationPlace + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", location='" + location + '\'' +
                ", vehicleCondition='" + vehicleCondition + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
