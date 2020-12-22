package com.example.sepatu_customer.network.repository.order;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.order.TransactionResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDetailDataOrderRepository {
    private ApiInterface apiInterface;

    public GetDetailDataOrderRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<TransactionResponse> getDetailDataOrder(String id_order){
        final MutableLiveData<TransactionResponse> mutableLiveData = new MutableLiveData<>();
        Call<TransactionResponse> requestOrder = apiInterface.getDetailDataOrder(id_order);
        requestOrder.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
