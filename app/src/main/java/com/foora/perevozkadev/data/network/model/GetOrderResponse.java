package com.foora.perevozkadev.data.network.model;

import com.foora.perevozkadev.ui.add_order.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Integer next;
    @SerializedName("previous")
    @Expose
    private Integer previous;

    @SerializedName("results")
    @Expose
    private List<Order> orders;

    public GetOrderResponse() {
    }

    public GetOrderResponse(Integer count, Integer next, Integer previous, List<Order> orders) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getPrevious() {
        return previous;
    }

    public void setPrevious(Integer previous) {
        this.previous = previous;
    }

}
