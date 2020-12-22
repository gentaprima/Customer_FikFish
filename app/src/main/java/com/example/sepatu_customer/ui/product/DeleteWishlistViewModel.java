package com.example.sepatu_customer.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.wishlist.DeleteWishlistRepository;

public class DeleteWishlistViewModel extends ViewModel {
    private DeleteWishlistRepository deleteWishlistRepository;

    public  DeleteWishlistViewModel(){
        deleteWishlistRepository = new DeleteWishlistRepository();
    }

    public LiveData<MessageOnly> deleteWishlist(String id_product,String id_users){
        return  deleteWishlistRepository.deleteWishlist(id_product,id_users);
    }
}
