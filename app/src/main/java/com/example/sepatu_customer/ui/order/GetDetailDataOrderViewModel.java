package com.example.sepatu_customer.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.order.TransactionResponse;
import com.example.sepatu_customer.network.repository.order.GetDataOrderRepository;
import com.example.sepatu_customer.network.repository.order.GetDetailDataOrderRepository;

public class GetDetailDataOrderViewModel extends ViewModel {

    private GetDetailDataOrderRepository getDetailDataOrderRepository;

    public GetDetailDataOrderViewModel(){
        getDetailDataOrderRepository = new GetDetailDataOrderRepository();
    }

    public LiveData<TransactionResponse> getDetailDataOrder(String id_order){
        return getDetailDataOrderRepository.getDetailDataOrder(id_order);
    }
}
