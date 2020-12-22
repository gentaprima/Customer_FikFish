package com.example.sepatu_customer.model;

import com.google.gson.annotations.SerializedName;

public class Users {

    private String id_users;
    private String username;
    private String full_name;
    private String email;
    private String password;
    private String role;
    private String users_id;
    private String tgl_lahir;
    private String jenis_kelamin;
    private String alamat;
    private String latitude;
    private String longtitude;
    private String photo;
    private String no_hp;

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getId_users() {
        return id_users;
    }

    public void setId_users(String id_users) {
        this.id_users = id_users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Users(String id_users, String username, String full_name, String email, String password, String role, String users_id, String tgl_lahir, String jenis_kelamin, String alamat, String latitude, String longtitude,String photo,String no_hp) {
        this.id_users = id_users;
        this.username = username;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.users_id = users_id;
        this.tgl_lahir = tgl_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.photo = photo;
        this.no_hp =  no_hp;
    }
}
