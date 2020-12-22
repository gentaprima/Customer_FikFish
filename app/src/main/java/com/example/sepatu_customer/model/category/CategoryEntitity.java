package com.example.sepatu_customer.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryEntitity {
    @SerializedName("id_jenis")
    @Expose
    private String idJenis;

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

    @SerializedName("nama_jenis")
    @Expose
    private String namaJenis;


}
