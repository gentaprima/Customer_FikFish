package com.example.sepatu_customer.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.cart.AddCartRepository;

public class AddCartViewModel extends ViewModel {
    private AddCartRepository addCartRepository;

    public AddCartViewModel(){
        addCartRepository = new AddCartRepository();
    }

    public LiveData<MessageOnly> addCart(String id_product,String id_users){
        return addCartRepository.addCart(id_product,id_users);
    }
}
