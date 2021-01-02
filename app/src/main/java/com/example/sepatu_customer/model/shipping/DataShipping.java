package com.example.sepatu_customer.model.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataShipping {
    @SerializedName("id_shipping")
    @Expose
    private String idShipping;
    @SerializedName("id_order")
    @Expose
    private String idOrder;
    @SerializedName("id_courier")
    @Expose
    private String idCourier;
    @SerializedName("is_delayed")
    @Expose
    private String isDelayed;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("date_shipping")
    @Expose
    private String dateShipping;
    @SerializedName("date_received")
    @Expose
    private String dateReceived;
    @SerializedName("status_shipping")
    @Expose
    private String statusShipping;

    public String getIdShipping() {
        return idShipping;
    }

    public void setIdShipping(String idShipping) {
        this.idShipping = idShipping;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdCourier() {
        return idCourier;
    }

    public void setIdCourier(String idCourier) {
        this.idCourier = idCourier;
    }

    public String getIsDelayed() {
        return isDelayed;
    }

    public void setIsDelayed(String isDelayed) {
        this.isDelayed = isDelayed;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDateShipping() {
        return dateShipping;
    }

    public void setDateShipping(String dateShipping) {
        this.dateShipping = dateShipping;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getStatusShipping() {
        return statusShipping;
    }

    public void setStatusShipping(String statusShipping) {
        this.statusShipping = statusShipping;
    }

}
