package com.example.sepatu_customer.network.repository.product;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.product.ProductResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductHomeRepository {
    private ApiInterface apiInterface;

    public ProductHomeRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<ProductResponse> getProductResponse(){
        final MutableLiveData<ProductResponse> mutableLiveData = new MutableLiveData<>();
        Call<ProductResponse> requestOrder = apiInterface.getDataProduct();
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
                System.out.println(t.getMessage());
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
}
