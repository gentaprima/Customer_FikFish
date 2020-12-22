package com.example.sepatu_customer.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.cart.DeleteCartRepository;

public class DeleteCartViewModel extends ViewModel {
    private DeleteCartRepository deleteCartRepository;

    public DeleteCartViewModel(){
        deleteCartRepository = new DeleteCartRepository();
    }

    public LiveData<MessageOnly> deleteCart(String id_users){
        return deleteCartRepository.deleteData(id_users);
    }
}
