package com.foora.perevozkadev.ui.add_order.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("loading_places")
    @Expose
    private List<Place> loadingPlaces = null;
    @SerializedName("unloading_places")
    @Expose
    private List<Place> unloadingPlaces = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("loading_date")
    @Expose
    private String loadingDate;
    @SerializedName("unloading_date")
    @Expose
    private String unloadingDate;
    @SerializedName("cargo_category")
    @Expose
    private long cargoType;
    @SerializedName("cargo_category_name")
    @Expose
    private String cargoTypeName;
    @SerializedName("cargo")
    @Expose
    private String cargo;
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
    @SerializedName("car_quantity")
    @Expose
    private Integer carQuantity;
    @SerializedName("cost")
    @Expose
    private Double cost;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("payment_type_1")
    @Expose
    private String paymentType1;
    @SerializedName("payment_type_2")
    @Expose
    private String paymentType2;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("docs")
    @Expose
    private String docs;
    @SerializedName("loading")
    @Expose
    private String loading;
    @SerializedName("conditions")
    @Expose
    private String conditions;
    @SerializedName("additionally")
    @Expose
    private String additionally;
    @SerializedName("skype")
    @Expose
    private String skype;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("telegram")
    @Expose
    private String telegram;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("add_info")
    @Expose
    private String additionalInfo;
    @SerializedName("user")
    @Expose
    private int user;
//    @SerializedName("status")
//    @Expose
//    private String status;
    /**
     * No args constructor for use in serialization
     *
     */
    public Order() {
    }

    public Order(int id, List<Place> loadingPlaces, List<Place> unloadingPlaces, String status, String loadingDate, String unloadingDate, String cargo, Double weightFrom, Double weightTo, Double volumeFrom, Double volumeTo, String transportType, Integer carQuantity, Double cost, String currency, Double distance, String size, String paymentType1, String paymentType2, String companyName, String contactPerson, String email, String docs, String loading, String conditions, String additionally, String skype, String phone, String telegram, String whatsapp, String additionalInfo) {
        this.id = id;
        this.loadingPlaces = loadingPlaces;
        this.unloadingPlaces = unloadingPlaces;
        this.status = status;
        this.loadingDate = loadingDate;
        this.unloadingDate = unloadingDate;
        this.cargo = cargo;
        this.weightFrom = weightFrom;
        this.weightTo = weightTo;
        this.volumeFrom = volumeFrom;
        this.volumeTo = volumeTo;
        this.transportType = transportType;
        this.carQuantity = carQuantity;
        this.cost = cost;
        this.currency = currency;
        this.distance = distance;
        this.size = size;
        this.paymentType1 = paymentType1;
        this.paymentType2 = paymentType2;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.docs = docs;
        this.loading = loading;
        this.conditions = conditions;
        this.additionally = additionally;
        this.skype = skype;
        this.phone = phone;
        this.telegram = telegram;
        this.whatsapp = whatsapp;
        this.additionalInfo = additionalInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getWeightFrom() {
        return weightFrom;
    }

    public void setWeightFrom(Double weightFrom) {
        this.weightFrom = weightFrom;
    }

    public Double getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(Double weightTo) {
        this.weightTo = weightTo;
    }

    public Double getVolumeFrom() {
        return volumeFrom;
    }

    public void setVolumeFrom(Double volumeFrom) {
        this.volumeFrom = volumeFrom;
    }

    public Double getVolumeTo() {
        return volumeTo;
    }

    public void setVolumeTo(Double volumeTo) {
        this.volumeTo = volumeTo;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public Integer getCarQuantity() {
        return carQuantity;
    }

    public void setCarQuantity(Integer carQuantity) {
        this.carQuantity = carQuantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPaymentType1() {
        return paymentType1;
    }

    public void setPaymentType1(String paymentType1) {
        this.paymentType1 = paymentType1;
    }

    public String getPaymentType2() {
        return paymentType2;
    }

    public void setPaymentType2(String paymentType2) {
        this.paymentType2 = paymentType2;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getAdditionally() {
        return additionally;
    }

    public void setAdditionally(String additionally) {
        this.additionally = additionally;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public long getCargoType() {
        return cargoType;
    }

    public void setCargoType(long cargoType) {
        this.cargoType = cargoType;
    }

    public String getCargoTypeName() {
        return cargoTypeName;
    }

    public void setCargoTypeName(String cargoTypeName) {
        this.cargoTypeName = cargoTypeName;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", loadingPlaces=" + loadingPlaces +
                ", unloadingPlaces=" + unloadingPlaces +
                ", status='" + status + '\'' +
                ", loadingDate='" + loadingDate + '\'' +
                ", unloadingDate='" + unloadingDate + '\'' +
                ", cargoType='" + cargoType + '\'' +
                ", cargoTypeName='" + cargoTypeName + '\'' +
                ", cargo='" + cargo + '\'' +
                ", weightFrom=" + weightFrom +
                ", weightTo=" + weightTo +
                ", volumeFrom=" + volumeFrom +
                ", volumeTo=" + volumeTo +
                ", transportType='" + transportType + '\'' +
                ", carQuantity=" + carQuantity +
                ", cost=" + cost +
                ", currency='" + currency + '\'' +
                ", distance=" + distance +
                ", size='" + size + '\'' +
                ", paymentType1='" + paymentType1 + '\'' +
                ", paymentType2='" + paymentType2 + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", email='" + email + '\'' +
                ", docs='" + docs + '\'' +
                ", loading='" + loading + '\'' +
                ", conditions='" + conditions + '\'' +
                ", additionally='" + additionally + '\'' +
                ", skype='" + skype + '\'' +
                ", phone='" + phone + '\'' +
                ", telegram='" + telegram + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
