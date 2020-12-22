package com.example.sepatu_customer.model.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCart {
    @SerializedName("id_cart")
    @Expose
    private String idCart;
    @SerializedName("id_product")
    @Expose
    private String idProduct;
    @SerializedName("id_users")
    @Expose
    private String idUsers;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("id_ikan")
    @Expose
    private String idIkan;
    @SerializedName("nama_ikan")
    @Expose
    private String namaIkan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("id_jenis")
    @Expose
    private String idJenis;
    @SerializedName("is_order")
    private String is_order;

    public String getIs_order() {
        return is_order;
    }

    public void setIs_order(String is_order) {
        this.is_order = is_order;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(String idUsers) {
        this.idUsers = idUsers;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIdIkan() {
        return idIkan;
    }

    public void setIdIkan(String idIkan) {
        this.idIkan = idIkan;
    }

    public String getNamaIkan() {
        return namaIkan;
    }

    public void setNamaIkan(String namaIkan) {
        this.namaIkan = namaIkan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(String idJenis) {
        this.idJenis = idJenis;
    }

}
