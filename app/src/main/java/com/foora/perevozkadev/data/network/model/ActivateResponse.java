package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class ActivateResponse extends TokenResponse{

    @SerializedName("sms_code")
    @Expose
    private String smsCode;

    public ActivateResponse(String token) {
        super(token);
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
