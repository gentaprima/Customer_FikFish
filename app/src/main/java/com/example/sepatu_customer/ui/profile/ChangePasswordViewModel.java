package com.example.sepatu_customer.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.profile.ChangePasswordRepository;

public class ChangePasswordViewModel extends ViewModel {
    private ChangePasswordRepository changePasswordRepository;

    public ChangePasswordViewModel(){
        changePasswordRepository = new ChangePasswordRepository();
    }

    public LiveData<MessageOnly> changePassword(String id_users, String old_password, String new_password, String confirm_password){
        return changePasswordRepository.changePassword(id_users,old_password,new_password,confirm_password);
    }

}
