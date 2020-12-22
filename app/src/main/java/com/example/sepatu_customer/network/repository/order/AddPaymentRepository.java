package com.example.sepatu_customer.network.repository.order;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPaymentRepository {
    private ApiInterface apiInterface;

    public AddPaymentRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> addPayment(MultipartBody.Part image, RequestBody id_order){
        final MutableLiveData<MessageOnly> mutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requestOrder = apiInterface.addPayment(image,id_order);
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
