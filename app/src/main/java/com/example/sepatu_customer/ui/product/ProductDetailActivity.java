package com.example.sepatu_customer.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.product.ProductEntitity;
import com.example.sepatu_customer.network.Constanta;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.cart.AddCartViewModel;
import com.example.sepatu_customer.ui.cart.CartActivity;
import com.example.sepatu_customer.utils.DialogClass;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ProductEntitity dataProduct;
    CardView cardSubmit;
    Toolbar toolbar;
    TextView tv_title,tv_jenis,tv_harga,tv_nama,tv_desc;
    ImageView iv_cart,imageProduct,iv_like,iv_cart2;
    String id_product,id_users;
    private SystemDataLocal systemDataLocal;
    private AddWishtlistViewModel addWishtlistViewModel;
    private CekWishlistViewModel cekWishlistViewModel;
    private AddCartViewModel addCartViewModel;
    private android.app.AlertDialog alertDialog;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        dataProduct = getIntent().getParcelableExtra("data");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_title = findViewById(R.id.title);
        tv_title.setText("Detail Ikan");
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart2 = findViewById(R.id.iv_cart2);
        imageProduct = findViewById(R.id.imageProduct);
        tv_jenis = findViewById(R.id.tv_jenis);
        cardSubmit = findViewById(R.id.cardSubmit);
        tv_harga = findViewById(R.id.tv_harga);
        tv_nama = findViewById(R.id.tv_nama);
        tv_desc = findViewById(R.id.tv_desc);
        iv_like = findViewById(R.id.iv_like);
        systemDataLocal = new SystemDataLocal(this);
        addWishtlistViewModel = ViewModelProviders.of(this).get(AddWishtlistViewModel.class);
        cekWishlistViewModel = ViewModelProviders.of(this).get(CekWishlistViewModel.class);
        addCartViewModel = ViewModelProviders.of(this).get(AddCartViewModel.class);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        tv_title.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Glide.with(getApplicationContext()).load(Constanta.BASE_URL_IMG + dataProduct.getFoto()).into(imageProduct);
        if(dataProduct != null){
            tv_jenis.setText(dataProduct.getNamaJenis());
            tv_harga.setText("Rp " + String.format("%,d",Integer.parseInt(dataProduct.getHarga())));
            tv_nama.setText("Ikan " + dataProduct.getNamaIkan());
            tv_desc.setText(dataProduct.getDeskripsi());
            id_product = dataProduct.getIdIkan();
        }
        cekWishlistViewModel.cekWishList(id_product,systemDataLocal.getLoginData().getId_users()).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    iv_like.setBackgroundResource(R.drawable.ic_like_active);
                }else{
                    iv_like.setBackgroundResource(R.drawable.ic_like_not);
                }
            }
        });

        iv_cart2.setVisibility(View.VISIBLE);
        iv_cart.setVisibility(View.GONE);
        iv_like.setOnClickListener(this);
        iv_cart2.setOnClickListener(this);
        cardSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_like){
            addWishlist();
        }else if(v.getId() == R.id.iv_cart2){
            startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
        }else if(v.getId() == R.id.cardSubmit){
            addCart();
        }
    }

    private void addCart() {
        String idUsers = systemDataLocal.getLoginData().getId_users();
        View myview = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,myview).create();
        alertDialog.show();
        addCartViewModel.addCart(id_product,idUsers).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void addWishlist() {
        String id_users = systemDataLocal.getLoginData().getId_users();
        if(!id_users.equals("")){
            addWishtlistViewModel.addWishlist(id_product,id_users).observe(this, new Observer<MessageOnly>() {
                @Override
                public void onChanged(MessageOnly messageOnly) {
                    if(messageOnly.getStatus()){
                        Toast toast = Toast.makeText(ProductDetailActivity.this, messageOnly.getMessage(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,290);
                        toast.show();

                        iv_like.setBackgroundResource(R.drawable.ic_like_active);

                    }else{
                        Toast toast = Toast.makeText(ProductDetailActivity.this, messageOnly.getMessage(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,290);
                        toast.show();
                        iv_like.setBackgroundResource(R.drawable.ic_like_not);

                    }
                }
            });
        }else{
            Toast toast = Toast.makeText(ProductDetailActivity.this, "Silahkan login terlebih dahulu ...", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,290);
            toast.show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cekWishlistViewModel.cekWishList(id_product,systemDataLocal.getLoginData().getId_users()).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    iv_like.setBackgroundResource(R.drawable.ic_like_active);
                }else{
                    iv_like.setBackgroundResource(R.drawable.ic_like_not);
                }
            }
        });

    }


}