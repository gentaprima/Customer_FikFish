package com.example.sepatu_customer.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductEntitity implements Parcelable {
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
    @SerializedName("nama_jenis")
    @Expose
    private String namaJenis;

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

    public String getNamaJenis() {
        return namaJenis;
    }

    public void setNamaJenis(String namaJenis) {
        this.namaJenis = namaJenis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idIkan);
        dest.writeString(this.namaIkan);
        dest.writeString(this.harga);
        dest.writeString(this.foto);
        dest.writeString(this.deskripsi);
        dest.writeString(this.idJenis);
        dest.writeString(this.namaJenis);
    }

    public ProductEntitity() {
    }

    protected ProductEntitity(Parcel in) {
        this.idIkan = in.readString();
        this.namaIkan = in.readString();
        this.harga = in.readString();
        this.foto = in.readString();
        this.deskripsi = in.readString();
        this.idJenis = in.readString();
        this.namaJenis = in.readString();
    }

    public static final Parcelable.Creator<ProductEntitity> CREATOR = new Parcelable.Creator<ProductEntitity>() {
        @Override
        public ProductEntitity createFromParcel(Parcel source) {
            return new ProductEntitity(source);
        }

        @Override
        public ProductEntitity[] newArray(int size) {
            return new ProductEntitity[size];
        }
    };
}
