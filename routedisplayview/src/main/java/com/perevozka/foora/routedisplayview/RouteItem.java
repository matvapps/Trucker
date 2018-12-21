package com.perevozka.foora.routedisplayview;

/**
 * Created by Alexandr.
 */
public class RouteItem {

    private String date;
    private String city;
    private String country;

    public RouteItem(String date, String city, String country) {
        this.date = date;
        this.city = city;
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
