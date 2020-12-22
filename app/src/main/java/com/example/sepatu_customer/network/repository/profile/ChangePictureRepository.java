package com.example.sepatu_customer.network.repository.profile;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePictureRepository {
    private ApiInterface apiInterface;

    public ChangePictureRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> changePicture(MultipartBody.Part image , RequestBody id_user){
        final MutableLiveData<MessageOnly> messageOnlyMutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requstOrder = apiInterface.updatePicture(image,id_user);
        requstOrder.enqueue(new Callback<MessageOnly>() {
            @Override
            public void onResponse(Call<MessageOnly> call, Response<MessageOnly> response) {
                if(response.body() != null){
                    messageOnlyMutableLiveData.postValue(response.body());
                }else{
                    messageOnlyMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageOnly> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return messageOnlyMutableLiveData;
    }
}
