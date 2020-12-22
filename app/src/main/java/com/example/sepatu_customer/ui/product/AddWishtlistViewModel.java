package com.example.sepatu_customer.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.wishlist.AddWishlistRepository;

public class AddWishtlistViewModel extends ViewModel {

    private AddWishlistRepository addWishlistRepository;

    public AddWishtlistViewModel(){
        addWishlistRepository = new AddWishlistRepository();
    }

    public LiveData<MessageOnly> addWishlist(String id_product,String id_users){
        return addWishlistRepository.addWishlist(id_product,id_users);
    }
}
