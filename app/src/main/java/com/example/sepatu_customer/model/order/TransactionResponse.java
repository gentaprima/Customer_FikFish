package com.example.sepatu_customer.model.order;

import com.example.sepatu_customer.model.cart.DataCart;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<DataTransaction> data = null;
    @SerializedName("total_harga")
    private String total_harga;

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<DataTransaction> getData() {
        return data;
    }

    public void setData(List<DataTransaction> data) {
        this.data = data;
    }
}
