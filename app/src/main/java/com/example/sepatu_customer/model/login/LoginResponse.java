package com.example.sepatu_customer.model.login;

import com.google.gson.annotations.SerializedName;

import java.security.PrivateKey;

public class LoginResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("users_data")
    private UsersData usersData;

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public UsersData getUsersData() {
        return usersData;
    }
}
