package com.example.sepatu_customer.model.wishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WishlistResponse {
    @SerializedName("data")
    @Expose
    private List<ProductWishlist> data = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public List<ProductWishlist> getData() {
        return data;
    }

    public void setData(List<ProductWishlist> data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
