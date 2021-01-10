package com.example.sepatu_customer.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.shipping.ShippingResponse;
import com.example.sepatu_customer.network.repository.shipping.DetailShippingRepository;

public class DetailShippingViewModel extends ViewModel {
    private DetailShippingRepository detailShippingRepository;

    public DetailShippingViewModel(){
        detailShippingRepository = new DetailShippingRepository();
    }

    public LiveData<ShippingResponse> detailShipping(String numberOrder){
        return detailShippingRepository.detailShipping(numberOrder);
    }
}
