package com.example.sepatu_customer.ui.checkout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.order.AddOrderRepository;
import com.example.sepatu_customer.ui.cart.AddCartViewModel;

public class AddOrderViewModel extends ViewModel {
    private AddOrderRepository addOrderRepository;

    public AddOrderViewModel(){
        addOrderRepository = new AddOrderRepository();
    }

    public LiveData<MessageOnly> addOrder(String id_users,String ongkir){
        return addOrderRepository.addOrder(id_users,ongkir);
    }

}
