package com.foora.perevozkadev.ui.messages.model;

/**
 * Created by Alexandr.
 */
public class Message {
    String name;
    String route;
    String message;
    String date;

    public Message(String name, String route, String message, String date) {
        this.name = name;
        this.route = route;
        this.message = message;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
