package com.example.sepatu_customer.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.profile.ChangePictureRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChangePictureViewModel extends ViewModel {
    private ChangePictureRepository changePictureRepository;

    public ChangePictureViewModel(){
        changePictureRepository = new ChangePictureRepository();
    }

    public LiveData<MessageOnly> changePicture(MultipartBody.Part image, RequestBody id_users){
        return changePictureRepository.changePicture(image,id_users);
    }
}
