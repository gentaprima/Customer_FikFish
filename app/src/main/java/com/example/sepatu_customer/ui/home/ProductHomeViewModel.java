package com.example.sepatu_customer.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.product.ProductResponse;
import com.example.sepatu_customer.network.repository.product.ProductHomeRepository;

public class ProductHomeViewModel extends ViewModel {
    private ProductHomeRepository productHomeRepository;

    public ProductHomeViewModel() {
        productHomeRepository = new ProductHomeRepository();
    }

    public LiveData<ProductResponse> getResponseProduct(){
        return productHomeRepository.getProductResponse();
    }
}
