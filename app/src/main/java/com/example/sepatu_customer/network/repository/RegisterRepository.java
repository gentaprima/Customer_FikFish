package com.example.sepatu_customer.network.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private ApiInterface apiInterface;

    public RegisterRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> getResponseRegister(String username,String full_name,String password,String email){
//        System.out.println(username + " " + full_name + " " + password + " " + email);
        final MutableLiveData<MessageOnly> responseRegister = new MutableLiveData<>();
        Call<MessageOnly> requestOrder = apiInterface.registerUser(username,full_name,password,email);
        requestOrder.enqueue(new Callback<MessageOnly>() {
            @Override
            public void onResponse(Call<MessageOnly> call, Response<MessageOnly> response) {
                if(response.body() != null){
                    System.out.println(response.body());
                    responseRegister.postValue(response.body());
                }else{
                    responseRegister.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageOnly> call, Throwable t) {
                System.out.println(t.getMessage());
                responseRegister.postValue(null);
            }
        });
        return responseRegister;
    }
}
