package com.example.sepatu_customer.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.wishlist.CekWishlistRepository;

public class CekWishlistViewModel extends ViewModel {
    private CekWishlistRepository cekWishlistRepository;

    public CekWishlistViewModel(){
        cekWishlistRepository = new CekWishlistRepository();
    }

    public LiveData<MessageOnly> cekWishList(String id_product,String id_users){
        return cekWishlistRepository.cekDataWish(id_product,id_users);
    }
}
