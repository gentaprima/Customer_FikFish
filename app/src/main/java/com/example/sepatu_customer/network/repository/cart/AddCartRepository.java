package com.example.sepatu_customer.network.repository.cart;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCartRepository {
    private ApiInterface apiInterface;

    public AddCartRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> addCart(String id_product,String id_users){
        final MutableLiveData<MessageOnly> mutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requesOrder = apiInterface.addCart(id_product,id_users);
        requesOrder.enqueue(new Callback<MessageOnly>() {
            @Override
            public void onResponse(Call<MessageOnly> call, Response<MessageOnly> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageOnly> call, Throwable t) {
                System.out.print(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
