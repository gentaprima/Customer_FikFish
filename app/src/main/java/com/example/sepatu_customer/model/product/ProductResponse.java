package com.example.sepatu_customer.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data_product")
    @Expose
    private List<ProductEntitity> dataProduct = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<ProductEntitity> getDataProduct() {
        return dataProduct;
    }

    public void setDataProduct(List<ProductEntitity> dataProduct) {
        this.dataProduct = dataProduct;
    }
}
