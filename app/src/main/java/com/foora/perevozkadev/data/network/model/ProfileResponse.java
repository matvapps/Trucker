package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexander Matvienko on 21.12.2018.
 */
public class ProfileResponse {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("passport_num")
    @Expose
    private Object passportNum;
    @SerializedName("passport_expiration_date")
    @Expose
    private Object passportExpirationDate;
    @SerializedName("reg_certificate_num")
    @Expose
    private Object regCertificateNum;
    @SerializedName("license_num")
    @Expose
    private Object licenseNum;
    @SerializedName("license_expiration_date")
    @Expose
    private Object licenseExpirationDate;
    @SerializedName("register_country")
    @Expose
    private Object registerCountry;
    @SerializedName("international_passport_photos")
    @Expose
    private List<Object> internationalPassportPhotos = null;
    @SerializedName("registration_certificate_photos")
    @Expose
    private List<Object> registrationCertificatePhotos = null;
    @SerializedName("transportation_license_photos")
    @Expose
    private List<Object> transportationLicensePhotos = null;
    @SerializedName("lang")
    @Expose
    private Object lang;
    @SerializedName("currency")
    @Expose
    private Object currency;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("use_notification")
    @Expose
    private Object useNotification;
    @SerializedName("is_2fa_enabled")
    @Expose
    private Boolean is2faEnabled;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(Object passportNum) {
        this.passportNum = passportNum;
    }

    public Object getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(Object passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    public Object getRegCertificateNum() {
        return regCertificateNum;
    }

    public void setRegCertificateNum(Object regCertificateNum) {
        this.regCertificateNum = regCertificateNum;
    }

    public Object getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(Object licenseNum) {
        this.licenseNum = licenseNum;
    }

    public Object getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    public void setLicenseExpirationDate(Object licenseExpirationDate) {
        this.licenseExpirationDate = licenseExpirationDate;
    }

    public Object getRegisterCountry() {
        return registerCountry;
    }

    public void setRegisterCountry(Object registerCountry) {
        this.registerCountry = registerCountry;
    }

    public List<Object> getInternationalPassportPhotos() {
        return internationalPassportPhotos;
    }

    public void setInternationalPassportPhotos(List<Object> internationalPassportPhotos) {
        this.internationalPassportPhotos = internationalPassportPhotos;
    }

    public List<Object> getRegistrationCertificatePhotos() {
        return registrationCertificatePhotos;
    }

    public void setRegistrationCertificatePhotos(List<Object> registrationCertificatePhotos) {
        this.registrationCertificatePhotos = registrationCertificatePhotos;
    }

    public List<Object> getTransportationLicensePhotos() {
        return transportationLicensePhotos;
    }

    public void setTransportationLicensePhotos(List<Object> transportationLicensePhotos) {
        this.transportationLicensePhotos = transportationLicensePhotos;
    }

    public Object getLang() {
        return lang;
    }

    public void setLang(Object lang) {
        this.lang = lang;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Object getUseNotification() {
        return useNotification;
    }

    public void setUseNotification(Object useNotification) {
        this.useNotification = useNotification;
    }

    public Boolean getIs2faEnabled() {
        return is2faEnabled;
    }

    public void setIs2faEnabled(Boolean is2faEnabled) {
        this.is2faEnabled = is2faEnabled;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", userType='" + userType + '\'' +
                ", country=" + country +
                ", passportNum=" + passportNum +
                ", passportExpirationDate=" + passportExpirationDate +
                ", regCertificateNum=" + regCertificateNum +
                ", licenseNum=" + licenseNum +
                ", licenseExpirationDate=" + licenseExpirationDate +
                ", registerCountry=" + registerCountry +
                ", internationalPassportPhotos=" + internationalPassportPhotos +
                ", registrationCertificatePhotos=" + registrationCertificatePhotos +
                ", transportationLicensePhotos=" + transportationLicensePhotos +
                ", lang=" + lang +
                ", currency=" + currency +
                ", description=" + description +
                ", rating=" + rating +
                ", useNotification=" + useNotification +
                ", is2faEnabled=" + is2faEnabled +
                '}';
    }
}
