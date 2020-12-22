package com.example.sepatu_customer.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.cart.PlusCartRepository;

public class PlusCartViewModel extends ViewModel {
    private PlusCartRepository plusCartRepository;

    public PlusCartViewModel(){
        plusCartRepository = new PlusCartRepository();
    }

    public LiveData<MessageOnly> plusCart(String id_cart){
        return plusCartRepository.plusCart(id_cart);
    }


}
