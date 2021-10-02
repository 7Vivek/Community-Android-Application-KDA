package com.project.kda.Model;

public class RegistrationModel {
    public String userId;
    public String name;
    public String email;
    public String password;
    public String mobile;
    public String region;

    public RegistrationModel(){

    }

    public RegistrationModel(String userId, String name, String email, String password, String mobile, String region) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.region = region;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
