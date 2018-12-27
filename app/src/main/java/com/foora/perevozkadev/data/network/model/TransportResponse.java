package com.foora.perevozkadev.data.network.model;

import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class TransportResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<Transport> transports = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransportResponse() {
    }

    /**
     *
     * @param transports
     * @param previous
     * @param count
     * @param next
     */
    public TransportResponse(Integer count, Object next, Object previous, List<Transport> transports) {
        super();
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.transports = transports;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        this.transports = transports;
    }

    @Override
    public String toString() {
        return "TransportResponse{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", transports=" + transports +
                '}';
    }
}
