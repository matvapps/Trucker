package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class RegisterResponse extends TokenResponse {

    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("password")
    @Expose
    private List<String> password = null;
    @SerializedName("username")
    @Expose
    private List<String> username = null;
    @SerializedName("user_type")
    @Expose
    private List<String> userType = null;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("group")
    @Expose
    private List<String> group = null;

    public RegisterResponse(String token) {
        super(token);
    }

    public RegisterResponse(String token, int userId, List<String> password, List<String> username, List<String> userType, List<String> phone, List<String> email, List<String> group) {
        super(token);
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.userType = userType;
        this.phone = phone;
        this.email = email;
        this.group = group;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getUserType() {
        return userType;
    }

    public void setUserType(List<String> userType) {
        this.userType = userType;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(List<String> group) {
        this.group = group;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getListOfErrors() {
        List<String> errors = new ArrayList<>();

        if (password != null && !password.isEmpty()) {
            errors.add("password");
            errors.addAll(password);
        }
        if (username != null && !username.isEmpty()) {
            errors.add("username");
            errors.addAll(username);
        }
        if (userType != null && !userType.isEmpty()) {
            errors.add("userType");
            errors.addAll(userType);
        }
        if (phone != null && !phone.isEmpty()) {
            errors.add("phone");
            errors.addAll(phone);
        }
        if (email != null && !email.isEmpty()) {
            errors.add("email");
            errors.addAll(email);
        }
        if (group != null && !group.isEmpty()) {
            errors.add("group");
            errors.addAll(group);
        }

        return errors;
    }

}
