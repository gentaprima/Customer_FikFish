package com.example.sepatu_customer.network.repository.cart;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCartRepository {
    private ApiInterface apiInterface;

    public DeleteCartRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> deleteData(String id_users){
        final MutableLiveData<MessageOnly> mutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requestOrder = apiInterface.deleteCart(id_users);
        requestOrder.enqueue(new Callback<MessageOnly>() {
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
                System.out.println(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
