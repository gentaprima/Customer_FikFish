package com.example.sepatu_customer.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.wishlist.WishlistResponse;
import com.example.sepatu_customer.network.repository.wishlist.ProductWishlistRepository;

public class ProductWishlistViewModel extends ViewModel {
    private ProductWishlistRepository productWishlistRepository;

    public ProductWishlistViewModel(){
        productWishlistRepository = new ProductWishlistRepository();
    }

    public LiveData<WishlistResponse> getWishlist(String id_users){
        return productWishlistRepository.getWishlist(id_users);
    }
}
