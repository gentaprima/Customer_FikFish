package com.example.sepatu_customer.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.category.CategoryResponse;
import com.example.sepatu_customer.network.repository.CategoryHomeRepository;

public class CategoryHomeViewModel extends ViewModel {
    private CategoryHomeRepository categoryHomeRepository;

    public CategoryHomeViewModel() {
        categoryHomeRepository = new CategoryHomeRepository();
    }

    public LiveData<CategoryResponse> getResponseCategory(){
        return categoryHomeRepository.getCategoryResponse();
    }
}
