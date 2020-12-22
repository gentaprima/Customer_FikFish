package com.example.sepatu_customer.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.order.CancelOrderRepository;

public class CancelOrderViewModel extends ViewModel {
    private CancelOrderRepository cancelOrderRepository;

    public CancelOrderViewModel(){
        cancelOrderRepository = new CancelOrderRepository();
    }

    public LiveData<MessageOnly> cancelOrder(String id_order){
        return cancelOrderRepository.cancelOrder(id_order);
    }
}
