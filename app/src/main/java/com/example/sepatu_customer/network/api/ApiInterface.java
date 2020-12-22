package com.example.sepatu_customer.network.api;

import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.cart.CartResponse;
import com.example.sepatu_customer.model.category.CategoryResponse;
import com.example.sepatu_customer.model.login.LoginResponse;
import com.example.sepatu_customer.model.order.OrderResponse;
import com.example.sepatu_customer.model.order.TransactionResponse;
import com.example.sepatu_customer.model.product.ProductResponse;
import com.example.sepatu_customer.model.wishlist.WishlistResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("users/register")
    Call<MessageOnly> registerUser(@Field("username") String username,
                                   @Field("full_name") String full_name,
                                   @Field("password") String password,
                                   @Field("email") String email);

    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> loginUser(@Field("username") String username,
                                  @Field("password") String password);

    @GET("product")
    Call<ProductResponse> getDataProduct();

    @GET("category")
    Call<CategoryResponse> getDataCategory();

    @FormUrlEncoded
    @POST("product/categoryProduct")
    Call<ProductResponse> getDataProductByCategory(@Field("id_jenis") String id_category);

    @FormUrlEncoded
    @POST("profile/changeProfile")
    Call<MessageOnly> updateProfile(@Field("id_users") String id_user,
                                    @Field("username") String username,
                                    @Field("full_name") String full_name,
                                    @Field("no_hp") String no_hp);

    @FormUrlEncoded
    @POST("profile/changePassword")
    Call<MessageOnly> updatePassword(@Field("id_users") String id_user,
                                     @Field("old_password")String old_password,
                                     @Field("new_password")String new_password,
                                     @Field("confirm_password") String confirm_password);

    @Multipart
    @POST("profile/changePicture")
    Call<MessageOnly> updatePicture(@Part MultipartBody.Part image,
                                    @Part("id_users")RequestBody id_users);

    @FormUrlEncoded
    @POST("profile/changeAddress")
    Call<MessageOnly> updateAddress(@Field("id_users") String id_users,
                                    @Field("alamat") String alamat,
                                    @Field("latitude")String latitude,
                                    @Field("longtitude")String longitude);

    @FormUrlEncoded
    @POST("wishlist/addData")
    Call<MessageOnly> addWishlist(@Field("id_product") String id_product,
                                  @Field("id_users") String id_users);

    @FormUrlEncoded
    @POST("wishlist/cekDataWish")
    Call<MessageOnly> cekWishList(@Field("id_product") String id_product,
                                  @Field("id_users") String id_users);

    @FormUrlEncoded
    @POST("wishlist/getData")
    Call<WishlistResponse> getDataWishlist(@Field("id_users") String id_users);

    @FormUrlEncoded
    @POST("cart/addCart")
    Call<MessageOnly> addCart(@Field("id_product") String id_product,
                              @Field("id_users")String id_users);

    @FormUrlEncoded
    @POST("cart/getDataCart")
    Call<CartResponse> getDataCart(@Field("id_users")String id_users);

    @FormUrlEncoded
    @POST("cart/deleteCart")
    Call<MessageOnly> deleteCart(@Field("id_users")String id_users);

    @FormUrlEncoded
    @POST("cart/plusCart")
    Call<MessageOnly> plusCart(@Field("id_cart") String id_cart);

    @FormUrlEncoded
    @POST("cart/minCart")
    Call<MessageOnly> minCart(@Field("id_cart") String id_cart);

    @FormUrlEncoded
    @POST("order/addOrder")
    Call<MessageOnly> addOrder(@Field("id_users") String id_users,
                               @Field("ongkir") String ongkir);

    @FormUrlEncoded
    @POST("order/getDataOrder")
    Call<OrderResponse> getDataOrder(@Field("id_users") String id_users);

    @FormUrlEncoded
    @POST("order/getDataOrderByOrderId")
    Call<TransactionResponse> getDetailDataOrder(@Field("id_order")String id_order);

    @Multipart
    @POST("order/payment")
    Call<MessageOnly> addPayment(@Part MultipartBody.Part image,
                                 @Part("id_order") RequestBody id_order);

    @FormUrlEncoded
    @POST("order/cancelOrder")
    Call<MessageOnly> cancelOrder(@Field("id_order") String id_order);


    @FormUrlEncoded
    @POST("shipping/resumeShipping")
    Call<MessageOnly> resumeShipping(@Field("number_order")String id_shipping,
                                     @Field("date")String date);

    @FormUrlEncoded
    @POST("wishlist/deleteFavorite")
    Call<MessageOnly> deleteWishlist(@Field("id_product") String id_product,
                                     @Field("id_users")String id_users);

    @FormUrlEncoded
    @POST("profile/changeEmail")
    Call<MessageOnly> changeEmail(@Field("oldEmail")String oldEmail,
                                  @Field("newEmail")String newEmail,
                                  @Field("confirmEmail")String confirmEmail,
                                  @Field("id_users")String id_users);
}
