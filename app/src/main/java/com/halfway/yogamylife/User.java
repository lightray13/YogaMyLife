package com.halfway.yogamylife;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("auth")
    @Expose
    private Auth auth;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("first_address")
    @Expose
    private String firstAddress;
    @SerializedName("second_address")
    @Expose
    private String secondAddress;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("verified")
    @Expose
    private boolean verified;
    @SerializedName("styles")
    @Expose
    private List<Integer> styles = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("role")
    @Expose
    private int role;
    @SerializedName("extra_info")
    @Expose
    private boolean extraInfo;
    @SerializedName("created")
    @Expose
    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Integer> getStyles() {
        return styles;
    }

    public void setStyles(List<Integer> styles) {
        this.styles = styles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(boolean extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public class Auth {

        @SerializedName("vk")
        @Expose
        private Vk vk;
        @SerializedName("fb")
        @Expose
        private Fb fb;

        public Vk getVk() {
            return vk;
        }

        public void setVk(Vk vk) {
            this.vk = vk;
        }

        public Fb getFb() {
            return fb;
        }

        public void setFb(Fb fb) {
            this.fb = fb;
        }

        }

    public class Fb {

        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("email")
        @Expose
        private String email;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

        public class Vk {

            @SerializedName("user_id")
            @Expose
            private int userId;
            @SerializedName("email")
            @Expose
            private String email;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }


    }

}
