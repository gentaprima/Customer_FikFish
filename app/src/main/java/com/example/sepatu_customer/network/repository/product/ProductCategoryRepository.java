package com.example.sepatu_customer.network.repository.product;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.product.ProductResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryRepository {
    private ApiInterface apiInterface;

    public ProductCategoryRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<ProductResponse> getResponseProdduct(String id_category){
        final MutableLiveData<ProductResponse> mutableLiveData = new MutableLiveData<>();
        Call<ProductResponse> requestOrder = apiInterface.getDataProductByCategory(id_category);
        requestOrder.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });

        return mutableLiveData;
    }
}
