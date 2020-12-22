package com.example.sepatu_customer.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.cart.MinCartRepository;

public class MinCartViewModel extends ViewModel {

    private MinCartRepository minCartRepository;

    public MinCartViewModel(){
        minCartRepository = new MinCartRepository();
    }

    public LiveData<MessageOnly> minCart(String id_cart){
        return minCartRepository.minCart(id_cart);
    }
}
