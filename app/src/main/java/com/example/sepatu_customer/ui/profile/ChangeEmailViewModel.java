package com.example.sepatu_customer.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.profile.ChangeEmailRepository;

public class ChangeEmailViewModel extends ViewModel {
    private ChangeEmailRepository changeEmailRepository;

    public ChangeEmailViewModel(){
        changeEmailRepository = new ChangeEmailRepository();
    }

    public LiveData<MessageOnly> changeEmail(String oldEmail,String newEmail,String confirmEmail,String idUsers){
        return changeEmailRepository.changeEmail(oldEmail,newEmail,confirmEmail,idUsers);
    }
}
