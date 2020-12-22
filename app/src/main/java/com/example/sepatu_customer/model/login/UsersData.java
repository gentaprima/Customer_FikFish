package com.example.sepatu_customer.model.login;

import com.google.gson.annotations.SerializedName;

public class UsersData {
    @SerializedName("id_users")
    private String id_users;
    @SerializedName("username")
    private String username;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;
    @SerializedName("users_id")
    private String users_id;
    @SerializedName("tgl_lahir")
    private String tgl_lahir;
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("photo")
    private String photo;
    @SerializedName("no_hp")
    private String no_hp;

    public String getId_users() {
        return id_users;
    }

    public String getUsername() {
        return username;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsers_id() {
        return users_id;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNo_hp() {
        return no_hp;
    }

    @SerializedName("longtitude")
    private String longtitude;

}
