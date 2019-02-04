package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexander Matvienko on 04.02.2019.
 */
public class RequestInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("transports")
    @Expose
    private List<Integer> transports = null;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("actions")
    @Expose
    private List<Action> actions = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getTransports() {
        return transports;
    }

    public void setTransports(List<Integer> transports) {
        this.transports = transports;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", transports=" + transports +
                ", orderId=" + orderId +
                ", actions=" + actions +
                '}';
    }
}
