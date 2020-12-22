package com.example.sepatu_customer.network.repository.cart;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.cart.CartResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDataCartRepository {

    private ApiInterface apiInterface;

    public GetDataCartRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<CartResponse> getDataCart(String id_users){
        final MutableLiveData<CartResponse> mutableLiveData = new MutableLiveData<>();
        Call<CartResponse> requestOrder = apiInterface.getDataCart(id_users);
        requestOrder.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                System.out.print(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
