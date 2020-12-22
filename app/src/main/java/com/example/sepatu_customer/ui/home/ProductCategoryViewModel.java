package com.example.sepatu_customer.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.product.ProductResponse;
import com.example.sepatu_customer.network.repository.product.ProductCategoryRepository;

public class ProductCategoryViewModel extends ViewModel {

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryViewModel() {
        productCategoryRepository = new ProductCategoryRepository();
    }

    public LiveData<ProductResponse> getResponseProduct(String id_category){
        return productCategoryRepository.getResponseProdduct(id_category);
    }
}
