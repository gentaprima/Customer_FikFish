package com.example.sepatu_customer.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.adapter.ProductCategoryAdapter;
import com.example.sepatu_customer.adapter.WishlistAdapter;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.wishlist.WishlistResponse;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.product.DeleteWishlistViewModel;
import com.example.sepatu_customer.ui.product.ProductWishlistViewModel;
import com.example.sepatu_customer.utils.DialogClass;


public class LikeFragment extends Fragment {

    //    ProgressBar progressBar;
    RecyclerView recyclerView;
    private SystemDataLocal systemDataLocal;
    private ProductWishlistViewModel productWishlistViewModel;
    private DeleteWishlistViewModel deleteWishlistViewModel;
    private String id_users;
    private Activity activity;
    ImageView iv_notif;
    TextView tv_notif;
    private android.app.AlertDialog alertDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        progressBar = view.findViewById(R.id.progres_bar);
        recyclerView = view.findViewById(R.id.recycle_view);
        systemDataLocal = new SystemDataLocal(getContext());
        iv_notif = view.findViewById(R.id.iv_notif);
        tv_notif = view.findViewById(R.id.tv_notif);
        productWishlistViewModel = ViewModelProviders.of(this).get(ProductWishlistViewModel.class);
        deleteWishlistViewModel = ViewModelProviders.of(this).get(DeleteWishlistViewModel.class);
        id_users = systemDataLocal.getLoginData().getId_users();
        if(id_users.equals("")){
            tv_notif.setVisibility(View.VISIBLE);
            iv_notif.setVisibility(View.VISIBLE);

        }else{
            loadData();
            tv_notif.setVisibility(View.GONE);
            iv_notif.setVisibility(View.GONE);

        }

    }

    private void loadData() {
        productWishlistViewModel.getWishlist(id_users).observe(this, new Observer<WishlistResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(WishlistResponse wishlistResponse) {
                if(wishlistResponse != null){
                    if(wishlistResponse.getData().size() > 0){
                        WishlistAdapter wishlistAdapter = new WishlistAdapter(activity,wishlistResponse.getData());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(wishlistAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        tv_notif.setVisibility(View.GONE);
                        iv_notif.setVisibility(View.GONE);
                        wishlistAdapter.setOnItemClickCallback(new ProductCategoryAdapter.OnItemClickCallBack() {
                            @Override
                            public void onItemClicked(String id_product) {
                                deleteFavorite(id_product);
                            }
                        });
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        tv_notif.setVisibility(View.VISIBLE);
                        iv_notif.setVisibility(View.VISIBLE);
                        tv_notif.setText("Daftar Produk Favorit anda masih kosong !");
                    }
                }
            }
        });
    }

    private void deleteFavorite(String id_product) {
        String idUsers = systemDataLocal.getLoginData().getId_users();
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(getContext(),v).create();
        alertDialog.show();

        deleteWishlistViewModel.deleteWishlist(id_product,idUsers).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    alertDialog.dismiss();
                    Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_SHORT).show();
                    loadData();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(systemDataLocal.getCheckLogin()){
            loadData();
        }else{
            recyclerView.setVisibility(View.GONE);
            tv_notif.setVisibility(View.VISIBLE);
            iv_notif.setVisibility(View.VISIBLE);
            tv_notif.setText("Silahkan Login untuk melihat daftar pesanan anda");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_like, container, false);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}