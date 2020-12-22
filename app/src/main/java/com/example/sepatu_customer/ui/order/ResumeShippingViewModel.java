package com.example.sepatu_customer.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.order.ResumeShippingRepository;

public class ResumeShippingViewModel extends ViewModel {
    private ResumeShippingRepository resumeShippingRepository;

    public  ResumeShippingViewModel(){
        resumeShippingRepository = new ResumeShippingRepository();
    }

    public LiveData<MessageOnly> resumeShipping(String id_shipping,String date){
        return resumeShippingRepository.resumeShipping(id_shipping,date);
    }
}
