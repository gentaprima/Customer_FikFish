package com.example.sepatu_customer.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sepatu_customer.model.Users;
import com.example.sepatu_customer.model.login.UsersData;

public class SystemDataLocal {
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String KEY_USER = "user";
    private static final String KEY_ADDR = "address";
    private static final String KEY_ADDR_SAVE = "address_save";
    private UsersData usersData;

    public SystemDataLocal(Context context,UsersData usersData) {
        this.context = context;
        this.usersData = usersData;
    }

    public SystemDataLocal(Context context) {
        this.context = context;
    }

    public void setSessionLogin(){
        sharedPreferences  = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id_users",usersData.getId_users());
        editor.putString("username",usersData.getUsername());
        editor.putString("full_name",usersData.getFull_name());
        editor.putString("password",usersData.getPassword());
        editor.putString("email",usersData.getEmail());
        editor.putString("role",usersData.getRole());
        editor.putString("tgl_lahir",usersData.getTgl_lahir());
        editor.putString("jenis_kelamin",usersData.getJenis_kelamin());
        editor.putString("alamat",usersData.getAlamat());
        editor.putString("latitude",usersData.getLatitude());
        editor.putString("longtitude",usersData.getLongtitude());
        editor.putString("photo",usersData.getPhoto());
        editor.putString("no_hp",usersData.getNo_hp());
        editor.putBoolean("login",true);
        editor.apply();
    }

    public void editAllSessionLogin(String id_users,String username,String full_name,String password,String email,String role,String tgl_lahir,String jenis_kelamin,String alamat,String latitude,String longitude,String photo,String no_hp){
        sharedPreferences  = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id_users",id_users);
        editor.putString("username",username);
        editor.putString("full_name",full_name);
        editor.putString("password",password);
        editor.putString("email",email);
        editor.putString("role",role);
        editor.putString("tgl_lahir",tgl_lahir);
        editor.putString("jenis_kelamin",jenis_kelamin);
        editor.putString("alamat",alamat);
        editor.putString("latitude",latitude);
        editor.putString("longtitude",longitude);
        editor.putString("photo",photo);
        editor.putString("no_hp",no_hp);
        editor.apply();
    }

    public Users getLoginData(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("id_users","");
        String username = sharedPreferences.getString("username","");
        String full_name = sharedPreferences.getString("full_name","");
        String email = sharedPreferences.getString("email","");
        String password = sharedPreferences.getString("password","");
        String role = sharedPreferences.getString("role","");
        String tgl_lahir = sharedPreferences.getString("tgl_lahir","");
        String jenis_kelamin = sharedPreferences.getString("jenis_kelamin","");
        String alamat = sharedPreferences.getString("alamat","");
        String latitude = sharedPreferences.getString("latitude","");
        String longtitude = sharedPreferences.getString("longtitude","");
        String photo = sharedPreferences.getString("photo","");
        String no_hp = sharedPreferences.getString("no_hp","");
        return new Users(id_user,username,full_name,email,password,role,id_user,tgl_lahir,jenis_kelamin,alamat,latitude,longtitude,photo,no_hp);
    }

    public boolean getCheckLogin(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login",false);
    }

    public void destroySession(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void setAddressData(String post_code,String kecamatan,String kota,String provinsi){
        sharedPreferences = context.getSharedPreferences(KEY_ADDR_SAVE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("post_code",post_code);
        editor.putString("kecamatan",kecamatan);
        editor.putString("kota",kota);
        editor.putString("provinsi",provinsi);
        editor.apply();
    }


    public void setCoordinate(String address,String latitude,String longtitude,String post_code,String kecamatan,String kota,String provinsi,String changed){
        sharedPreferences = context.getSharedPreferences(KEY_ADDR,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("addr",address);
        editor.putString("post_code",post_code);
        editor.putString("kecamatan",kecamatan);
        editor.putString("kota",kota);
        editor.putString("provinsi",provinsi);
        editor.putString("latitude",latitude);
        editor.putString("longtitude",longtitude);
        editor.putString("changed",changed);
        editor.apply();
    }
    public void destroyCoordinate() {
        sharedPreferences = context.getSharedPreferences(KEY_ADDR, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public SharedPreferences getCoordinate() {
        sharedPreferences = context.getSharedPreferences(KEY_ADDR, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
}
