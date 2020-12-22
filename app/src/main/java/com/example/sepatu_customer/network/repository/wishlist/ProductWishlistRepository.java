package com.example.sepatu_customer.network.repository.wishlist;

import androidx.lifecycle.MutableLiveData;

import com.example.sepatu_customer.model.wishlist.WishlistResponse;
import com.example.sepatu_customer.network.api.ApiClient;
import com.example.sepatu_customer.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductWishlistRepository {
    private ApiInterface apiInterface;

    public  ProductWishlistRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<WishlistResponse> getWishlist(String id_users){
        final MutableLiveData<WishlistResponse> mutableLiveData = new MutableLiveData<>();
        Call<WishlistResponse> requestOrder = apiInterface.getDataWishlist(id_users);
        requestOrder.enqueue(new Callback<WishlistResponse>() {
            @Override
            public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                if(response.body() != null){
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<WishlistResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
