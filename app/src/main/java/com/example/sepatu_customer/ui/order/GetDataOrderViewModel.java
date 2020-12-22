package com.example.sepatu_customer.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.order.OrderResponse;
import com.example.sepatu_customer.network.repository.order.GetDataOrderRepository;
import com.example.sepatu_customer.ui.cart.GetDataCartViewModel;

public class GetDataOrderViewModel extends ViewModel {
    private GetDataOrderRepository getDataOrderRepository;

    public GetDataOrderViewModel(){
        getDataOrderRepository = new GetDataOrderRepository();
    }

    public LiveData<OrderResponse> getDataOrder(String id_users){
        return getDataOrderRepository.getDataOrder(id_users);
    }
}
