package com.example.sepatu_customer.ui.order.payment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.repository.order.AddPaymentRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPaymentViewModel extends ViewModel {
    private AddPaymentRepository addPaymentRepository;

    public AddPaymentViewModel(){
        addPaymentRepository = new AddPaymentRepository();
    }

    public LiveData<MessageOnly> addPayment(MultipartBody.Part image, RequestBody id_order){
        return addPaymentRepository.addPayment(image,id_order);
    }
}
