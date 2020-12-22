package com.example.sepatu_customer.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.login.LoginResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiInterface apiInterface;

    public LoginRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<LoginResponse> getResponseLogin(String username,String password){
        final MutableLiveData<LoginResponse> mutableLiveData = new MutableLiveData<>();
        Call<LoginResponse> requestOrder = apiInterface.loginUser(username,password);
        requestOrder.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
}
