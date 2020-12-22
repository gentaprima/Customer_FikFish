package com.example.sepatu_customer.ui.Register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.RegisterRepository;

public class RegisterViewModel extends ViewModel {
    private RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository = new RegisterRepository();
    }

    public LiveData<MessageOnly> getRegisterResponse(String username,String full_name,String password,String email){
        return registerRepository.getResponseRegister(username,full_name,password,email);
    }
}
