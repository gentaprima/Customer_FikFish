package com.example.sepatu_customer.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.category.CategoryResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryHomeRepository  {
    private ApiInterface apiInterface;

    public CategoryHomeRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<CategoryResponse> getCategoryResponse(){
        final MutableLiveData<CategoryResponse> mutableLiveData = new MutableLiveData<>();
        Call<CategoryResponse> requestOrder = apiInterface.getDataCategory();
        requestOrder.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
}
