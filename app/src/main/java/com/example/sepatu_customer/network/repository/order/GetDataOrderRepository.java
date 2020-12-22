package com.example.sepatu_customer.network.repository.order;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.order.OrderResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDataOrderRepository {
    private ApiInterface apiInterface;

    public GetDataOrderRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<OrderResponse> getDataOrder(String id_users){
        final MutableLiveData<OrderResponse> mutableLiveData = new MutableLiveData<>();
        Call<OrderResponse> requestOrder = apiInterface.getDataOrder(id_users);
        requestOrder.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
