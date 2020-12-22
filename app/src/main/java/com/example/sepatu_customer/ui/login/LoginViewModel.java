package com.example.sepatu_customer.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.login.LoginResponse;
import com.example.sepatu_customer.network.repository.LoginRepository;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public LiveData<LoginResponse> getLoginResponse(String username,String password){
        return loginRepository.getResponseLogin(username,password);
    }
}
