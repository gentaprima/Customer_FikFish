package com.example.sepatu_customer.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.profile.ChangeAddressRepository;

public class ChangeAddressViewModel  extends ViewModel {
    private ChangeAddressRepository changeAddressRepository;

    public ChangeAddressViewModel(){
        changeAddressRepository = new ChangeAddressRepository();
    }

    public LiveData<MessageOnly> updateAddress(String id_users,String alamat,String latitude,String longitude){
        return changeAddressRepository.changeAddress(id_users,alamat,latitude,longitude);
    }
}
