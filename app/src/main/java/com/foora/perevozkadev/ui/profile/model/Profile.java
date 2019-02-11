package com.foora.perevozkadev.ui.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Matvienko on 21.12.2018.
 */
public class Profile implements Serializable {

    @SerializedName("id")
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
    private String country;
    @SerializedName("passport_num")
    @Expose
    private String passportNum;
    @SerializedName("passport_expiration_date")
    @Expose
    private String passportExpirationDate;
    @SerializedName("reg_certificate_num")
    @Expose
    private String regCertificateNum;
    @SerializedName("license_num")
    @Expose
    private String licenseNum;
    @SerializedName("license_expiration_date")
    @Expose
    private String licenseExpirationDate;
    @SerializedName("register_country")
    @Expose
    private String registerCountry;
    @SerializedName("international_passport_photos")
    @Expose
    private List<String> internationalPassportPhotos = null;
    @SerializedName("registration_certificate_photos")
    @Expose
    private List<String> registrationCertificatePhotos = null;
    @SerializedName("transportation_license_photos")
    @Expose
    private List<String> transportationLicensePhotos = null;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("use_notification")
    @Expose
    private String useNotification;
    @SerializedName("is_2fa_enabled")
    @Expose
    private Boolean is2faEnabled;
    @SerializedName("group")
    @Expose
    private String userGroup;
    @SerializedName("location_latitude")
    @Expose
    private double latitude;
    @SerializedName("location_longitude")
    @Expose
    private double longitude;
    @SerializedName("profile_name")
    @Expose
    private String profileName;
    @SerializedName("groups")
    @Expose
    private List<String> groups;


    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public String getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(String passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    public String getRegCertificateNum() {
        return regCertificateNum;
    }

    public void setRegCertificateNum(String regCertificateNum) {
        this.regCertificateNum = regCertificateNum;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    public void setLicenseExpirationDate(String licenseExpirationDate) {
        this.licenseExpirationDate = licenseExpirationDate;
    }

    public String getRegisterCountry() {
        return registerCountry;
    }

    public void setRegisterCountry(String registerCountry) {
        this.registerCountry = registerCountry;
    }

    public List<String> getInternationalPassportPhotos() {
        return internationalPassportPhotos;
    }

    public void setInternationalPassportPhotos(List<String> internationalPassportPhotos) {
        this.internationalPassportPhotos = internationalPassportPhotos;
    }

    public List<String> getRegistrationCertificatePhotos() {
        return registrationCertificatePhotos;
    }

    public void setRegistrationCertificatePhotos(List<String> registrationCertificatePhotos) {
        this.registrationCertificatePhotos = registrationCertificatePhotos;
    }

    public List<String> getTransportationLicensePhotos() {
        return transportationLicensePhotos;
    }

    public void setTransportationLicensePhotos(List<String> transportationLicensePhotos) {
        this.transportationLicensePhotos = transportationLicensePhotos;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public void setUseNotification(String useNotification) {
        this.useNotification = useNotification;
    }

    public Boolean getIs2faEnabled() {
        return is2faEnabled;
    }

    public void setIs2faEnabled(Boolean is2faEnabled) {
        this.is2faEnabled = is2faEnabled;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", userType='" + userType + '\'' +
                ", country='" + country + '\'' +
                ", passportNum='" + passportNum + '\'' +
                ", passportExpirationDate='" + passportExpirationDate + '\'' +
                ", regCertificateNum='" + regCertificateNum + '\'' +
                ", licenseNum='" + licenseNum + '\'' +
                ", licenseExpirationDate='" + licenseExpirationDate + '\'' +
                ", registerCountry='" + registerCountry + '\'' +
                ", internationalPassportPhotos=" + internationalPassportPhotos +
                ", registrationCertificatePhotos=" + registrationCertificatePhotos +
                ", transportationLicensePhotos=" + transportationLicensePhotos +
                ", lang='" + lang + '\'' +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", useNotification='" + useNotification + '\'' +
                ", is2faEnabled=" + is2faEnabled +
                ", userGroup='" + userGroup + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
