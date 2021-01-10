package com.example.sepatu_customer.network.repository.shipping;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.shipping.ShippingResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailShippingRepository {
    private ApiInterface apiInterface;

    public DetailShippingRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<ShippingResponse> detailShipping(String numberOrder){
        final MutableLiveData<ShippingResponse> mutableLiveData = new MutableLiveData<>();
        Call<ShippingResponse> requestOrder = apiInterface.detailShipping(numberOrder);
        requestOrder.enqueue(new Callback<ShippingResponse>() {
            @Override
            public void onResponse(Call<ShippingResponse> call, Response<ShippingResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ShippingResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
