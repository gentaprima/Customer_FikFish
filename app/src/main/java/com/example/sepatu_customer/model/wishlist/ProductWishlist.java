package com.example.sepatu_customer.model.wishlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductWishlist implements Parcelable {
    @SerializedName("id_wishlist")
    private String idWishlist;
    @SerializedName("id_product")
    private String idProduct;
    @SerializedName("id_users")
    private String idUsers;
    @SerializedName("id_ikan")
    private String idIkan;
    @SerializedName("nama_ikan")
    private String namaIkan;
    @SerializedName("harga")
    private String harga;
    @SerializedName("foto")
    private String foto;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("id_jenis")
    private String idJenis;

    public String getIdWishlist() {
        return idWishlist;
    }

    public void setIdWishlist(String idWishlist) {
        this.idWishlist = idWishlist;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idWishlist);
        dest.writeString(this.idProduct);
        dest.writeString(this.idUsers);
        dest.writeString(this.idIkan);
        dest.writeString(this.namaIkan);
        dest.writeString(this.harga);
        dest.writeString(this.foto);
        dest.writeString(this.deskripsi);
        dest.writeString(this.idJenis);
    }

    public ProductWishlist() {
    }

    protected ProductWishlist(Parcel in) {
        this.idWishlist = in.readString();
        this.idProduct = in.readString();
        this.idUsers = in.readString();
        this.idIkan = in.readString();
        this.namaIkan = in.readString();
        this.harga = in.readString();
        this.foto = in.readString();
        this.deskripsi = in.readString();
        this.idJenis = in.readString();
    }

    public static final Parcelable.Creator<ProductWishlist> CREATOR = new Parcelable.Creator<ProductWishlist>() {
        @Override
        public ProductWishlist createFromParcel(Parcel source) {
            return new ProductWishlist(source);
        }

        @Override
        public ProductWishlist[] newArray(int size) {
            return new ProductWishlist[size];
        }
    };
}
