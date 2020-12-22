package com.example.sepatu_customer.network.repository.profile;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {
    private ApiInterface apiInterface;

    public ChangePasswordRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> changePassword(String id_user,String old_password,String new_password,String confirm_password){
        final MutableLiveData<MessageOnly> mutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requestOrder = apiInterface.updatePassword(id_user,old_password,new_password,confirm_password);
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
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
}
