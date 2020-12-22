package com.example.sepatu_customer.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.profile.UpdateProfileRepository;

public class ChangeProfileViewModel extends ViewModel {
    private UpdateProfileRepository updateProfileRepository;
    public ChangeProfileViewModel(){
        updateProfileRepository = new UpdateProfileRepository();
    }

    public LiveData<MessageOnly> updateProfileUsers(String id_user,String username,String full_name,String no_hp){
        return updateProfileRepository.updateProfileUsers(id_user,username,full_name,no_hp);
    }
}
