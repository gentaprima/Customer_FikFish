package com.example.sepatu_customer.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.cart.CartResponse;
import com.example.sepatu_customer.network.repository.cart.GetDataCartRepository;

public class GetDataCartViewModel extends ViewModel {
    private GetDataCartRepository getDataCartRepository;

    public GetDataCartViewModel(){
        getDataCartRepository = new GetDataCartRepository();
    }

    public LiveData<CartResponse> getDataCart(String id_users){
        return getDataCartRepository.getDataCart(id_users);
    }
}
