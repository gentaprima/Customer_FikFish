package com.example.sepatu_customer.ui.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.adapter.CartAdapter;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.cart.CartResponse;
import com.example.sepatu_customer.model.cart.DataCart;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.checkout.CheckoutActivity;
import com.example.sepatu_customer.ui.home.HomeActivity;
import com.example.sepatu_customer.ui.profile.ChangeAddressActivity;
import com.example.sepatu_customer.ui.profile.ChangeProfileActivity;
import com.example.sepatu_customer.utils.DialogClass;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private SystemDataLocal systemDataLocal;
    private GetDataCartViewModel cartViewModel;
    RecyclerView rv_product;
    Toolbar toolbar;
    TextView tv_title,tv_subtotal,tv_total,tv_ongkir;
    ImageView imageCart,iv_delete;
    CartActivity activity;
    View myview;
    private Button btn_checkout;
    private String id_users;
    private DeleteCartViewModel  deleteCartViewModel;
    private PlusCartViewModel plusCartViewModel;
    private MinCartViewModel minCartViewModel;
    private android.app.AlertDialog alertDialog;
    ProgressBar progressBar_total,rv_progressbar,progressBar_total2;
    private Double latShop,lotShop,latUsers,lotUsers;
    AlertDialog.Builder builder;
    private int ongkosKirim;
    int jumlahOrder = 0;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        systemDataLocal = new SystemDataLocal(this);
        cartViewModel = ViewModelProviders.of(this).get(GetDataCartViewModel.class);
        deleteCartViewModel  = ViewModelProviders.of(this).get(DeleteCartViewModel.class);
        plusCartViewModel  = ViewModelProviders.of(this).get(PlusCartViewModel.class);
        minCartViewModel  = ViewModelProviders.of(this).get(MinCartViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_title = findViewById(R.id.title);
        imageCart = findViewById(R.id.iv_cart);
        iv_delete = findViewById(R.id.iv_delete);
        tv_subtotal = findViewById(R.id.tv_subtotal);
        tv_total = findViewById(R.id.tv_total);
        rv_product = findViewById(R.id.rv_product);
        tv_ongkir = findViewById(R.id.tv_ongkir);
        imageCart.setVisibility(View.GONE);
        iv_delete.setVisibility(View.VISIBLE);
        progressBar_total = findViewById(R.id.progres_bar_total);
        progressBar_total2 = findViewById(R.id.sub_total_progress);
        rv_progressbar = findViewById(R.id.rv_progressbar);
        btn_checkout = findViewById(R.id.btn_checkout);
        id_users = systemDataLocal.getLoginData().getId_users();
        if(getSupportActionBar() != null ){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationIcon(R.drawable.ic_back_button_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_title.setText("My Cart");
        loadData();
        iv_delete.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);
        Sprite rotating = new ChasingDots();
        rv_progressbar.setIndeterminateDrawable(rotating);
        Sprite three = new ThreeBounce();
        Sprite three2 = new ThreeBounce();
        progressBar_total.setIndeterminateDrawable(three);
        progressBar_total2.setIndeterminateDrawable(three2);

        latShop = -6.229460;
        lotShop = 106.884471;
        String latitude = systemDataLocal.getLoginData().getLatitude();
        String longitude = systemDataLocal.getLoginData().getLongtitude();
        if(latitude != null){
            latUsers = Double.parseDouble(latitude);
        }
        if(longitude != null){
            lotUsers = Double.parseDouble(longitude);
        }
        LatLng from = new LatLng(latShop,lotShop);
        LatLng to = new LatLng(latUsers,lotUsers);
        Double distance = ((SphericalUtil.computeDistanceBetween(from,to)) / 1000) + 1.4;
        System.out.println(distance);

        if(distance <= 1){
             ongkosKirim = 3000;
        }else{
            String convertDistance = String.valueOf(distance).substring(0,1);
            ongkosKirim = Integer.parseInt(convertDistance) * 3000;
        }



        if(systemDataLocal.getLoginData().getLatitude().equals("0")){
            tv_ongkir.setText("Rp 0");
        }else{

        tv_ongkir.setText("Rp " + String.format("%,d",Integer.parseInt(String.valueOf(ongkosKirim))));
        }
    }

    private void loadData() {

        cartViewModel.getDataCart(id_users).observe(this, new Observer<CartResponse>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onChanged(CartResponse cartResponse) {
                if(cartResponse != null){

                    if(cartResponse.getData().size() > 0){
                        jumlahOrder = cartResponse.getData().size();
                        readData(cartResponse.getData());
                        if(cartResponse.getTotal_harga() != null){

                            tv_subtotal.setText("Rp " + String.format("%,d",Integer.parseInt(cartResponse.getTotal_harga())));
                        }else{
                            tv_subtotal.setText("Rp 0");
                        }
                        if(!cartResponse.getTotal_harga().equals("")){
                            int total = Integer.parseInt(cartResponse.getTotal_harga());
                            int totalAfterOngkir = total + ongkosKirim;
                            tv_total.setText("Rp " + String.format("%,d",Integer.parseInt(String.valueOf(totalAfterOngkir))));
                        }else{
                            tv_total.setText("Rp " + String.format("%,d",Integer.parseInt(cartResponse.getTotal_harga())));
                        }

                    }else{
                        jumlahOrder = 0;
                    }
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        String latitude = systemDataLocal.getLoginData().getLatitude();
        String longitude = systemDataLocal.getLoginData().getLongtitude();

        if(latitude != null){
            latUsers = Double.parseDouble(latitude);
        }
        if(longitude != null){
            lotUsers = Double.parseDouble(longitude);
        }
        LatLng from = new LatLng(latShop,lotShop);
        LatLng to = new LatLng(latUsers,lotUsers);
        Double distance = ((SphericalUtil.computeDistanceBetween(from,to)) / 1000) + 1.4;

        if(distance <= 1){
            ongkosKirim = 3000;
        }else{
            String convertDistance = String.valueOf(distance).substring(0,1);
            ongkosKirim = Integer.parseInt(convertDistance) * 3000;
        }
        if(systemDataLocal.getLoginData().getLatitude().equals("0")){
            tv_ongkir.setText("Rp 0");
        }else{

            tv_ongkir.setText("Rp " + String.format("%,d",Integer.parseInt(String.valueOf(ongkosKirim))));
        }
    }

    private void readData(List<DataCart> data) {
        CartAdapter cartAdapter = new CartAdapter(this,data);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv_product.setLayoutManager(lm);
        rv_product.setAdapter(cartAdapter);
        cartAdapter.setOnItemClickCallback(new CartAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(String id_cart) {
               plusCart(id_cart);
            }

            @Override
            public void onItemClickedMin(String id_cart) {
                minCart(id_cart);
            }
        });
    }

    private void displayDialog(String detail, String button, final String move){
        builder = new AlertDialog.Builder(this);
        myview = getLayoutInflater().inflate(R.layout.dialog_alamat,null,false);
        builder.setView(myview);
        TextView tv_detail = myview.findViewById(R.id.tv_detail);
        Button btn_alamat = myview.findViewById(R.id.btn_alamat);
        tv_detail.setText(detail);
        btn_alamat.setText(button);
        btn_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(move.equals("alamat")){
                    startActivity(new Intent(CartActivity.this, ChangeAddressActivity.class));
                }else if(move.equals("phone")){
                    startActivity(new Intent(CartActivity.this, ChangeProfileActivity.class));
                }else{
                    startActivity(new Intent(CartActivity.this, HomeActivity.class));
                }

            }
        });
        builder.show();

    }

    private void minCart(String id_cart) {
        rv_product.setVisibility(View.GONE);
        rv_progressbar.setVisibility(View.VISIBLE);
        tv_subtotal.setVisibility(View.GONE);
        tv_total.setVisibility(View.GONE);
        progressBar_total.setVisibility(View.VISIBLE);
        progressBar_total2.setVisibility(View.VISIBLE);
        minCartViewModel.minCart(id_cart).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    rv_progressbar.setVisibility(View.GONE);
                    rv_product.setVisibility(View.VISIBLE);
                    tv_subtotal.setVisibility(View.VISIBLE);
                    tv_total.setVisibility(View.VISIBLE);
                    progressBar_total.setVisibility(View.GONE);
                    progressBar_total2.setVisibility(View.GONE);
                    loadData();
                }
            }
        });
    }

    private void plusCart(String id_cart) {
        rv_product.setVisibility(View.GONE);
        rv_progressbar.setVisibility(View.VISIBLE);
        tv_subtotal.setVisibility(View.GONE);
        tv_total.setVisibility(View.GONE);
        progressBar_total.setVisibility(View.VISIBLE);
        progressBar_total2.setVisibility(View.VISIBLE);
        plusCartViewModel.plusCart(id_cart).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    rv_progressbar.setVisibility(View.GONE);
                    rv_product.setVisibility(View.VISIBLE);
                    tv_subtotal.setVisibility(View.VISIBLE);
                    tv_total.setVisibility(View.VISIBLE);
                    progressBar_total.setVisibility(View.GONE);
                    progressBar_total2.setVisibility(View.GONE);
                    loadData();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_delete:
                deleteCart();
                loadData();
                Intent refresh = new Intent(this, CartActivity.class);
                startActivity(refresh);
                this.finish();
                break;

            case R.id.btn_checkout:
                if(systemDataLocal.getLoginData().getAlamat().equals("")){
                    String detail = "Lengkapi Alamat Terlebih Dahulu ...";
                    String button = "Tambah Alamat";
                    String move = "alamat";
                    displayDialog(detail,button,move);
                }else if(systemDataLocal.getLoginData().getNo_hp().equals("")){
                    String detail = "Lengkapi No Telepon Terlebih Dahulu ...";
                    String button = "Tambah No Telepon";
                    String move = "phone";
                    displayDialog(detail,button,move);
                }else{
                    if(jumlahOrder > 0){
                        Intent moveCheckout = new Intent(CartActivity.this,CheckoutActivity.class);
                        moveCheckout.putExtra("ongkir",ongkosKirim);
                        startActivity(moveCheckout);
                    }else {
                        String detail = "Silahkan tambahkan produk yang ingin dipesan terlebih dahulu ...";
                        String button = "Cari Produk";
                        String move = "home";
                        displayDialog(detail,button,move);
                    }

                    break;
                }

        }
    }

    private void deleteCart() {
        View myview = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,myview).create();
        alertDialog.show();
        deleteCartViewModel.deleteCart(id_users).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
                loadData();
            }
        });
    }
}