package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class LoginResponse extends TokenResponse {

    @SerializedName("phone")
    @Expose
    String phone;

    public LoginResponse(String token) {
        super(token);
    }

    public LoginResponse(String token, String phone) {
        super(token);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
