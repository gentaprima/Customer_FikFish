package com.example.sepatu_customer.ui.checkout;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.adapter.CheckoutAdapter;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.cart.CartResponse;
import com.example.sepatu_customer.model.cart.DataCart;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.cart.GetDataCartViewModel;
import com.example.sepatu_customer.ui.confirm_order.ConfirmActivity;
import com.example.sepatu_customer.utils.DialogClass;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private SystemDataLocal systemDataLocal;
    Toolbar toolbar;
    private ImageView iv_cart;
    private TextView tv_title,tv_nama,tv_alamat,tv_total_harga,tv_phone;
    private GetDataCartViewModel getDataCartViewModel;
    private RecyclerView rv_product;
    private Button btn_submit;
    private AddOrderViewModel addOrderViewModel;
    private android.app.AlertDialog alertDialog;
    private int ongkir;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        toolbar = findViewById(R.id.toolbar);
        tv_nama = findViewById(R.id.tv_nama);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_phone = findViewById(R.id.tv_phone);
        tv_total_harga = findViewById(R.id.tv_totalharga);
        tv_title = findViewById(R.id.title);
        iv_cart = findViewById(R.id.iv_cart);
        rv_product = findViewById(R.id.rv_product);
        btn_submit = findViewById(R.id.btn_submit);
        getDataCartViewModel = ViewModelProviders.of(this).get(GetDataCartViewModel.class);
        addOrderViewModel = ViewModelProviders.of(this).get(AddOrderViewModel.class);
        setSupportActionBar(toolbar);
        tv_title.setText("Checkout");
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

        systemDataLocal = new SystemDataLocal(this);
        iv_cart.setVisibility(View.GONE);
        tv_nama.setText(systemDataLocal.getLoginData().getFull_name());
        tv_phone.setText("("+systemDataLocal.getLoginData().getNo_hp() + ")");
        tv_alamat.setText(systemDataLocal.getLoginData().getAlamat().substring(0,60) + " ...");

        loadData();

        btn_submit.setOnClickListener(this);
        ongkir = getIntent().getIntExtra("ongkir",0);
    }

    private void loadData() {
        getDataCartViewModel.getDataCart(systemDataLocal.getLoginData().getId_users()).observe(this, new Observer<CartResponse>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onChanged(CartResponse cartResponse) {
                if(cartResponse.getData().size() > 0){
                    readData(cartResponse.getData());
                    int total = Integer.parseInt(cartResponse.getTotal_harga());
                    int totalAfterOngkir = total + ongkir;

                    tv_total_harga.setText("Rp " + String.format("%,d",Integer.parseInt(String.valueOf(totalAfterOngkir))));
                }
            }
        });
    }

    private void readData(List<DataCart> data) {
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(this,data);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv_product.setLayoutManager(lm);
        rv_product.setAdapter(checkoutAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_submit){
            addOrder();
        }
    }

    private void addOrder() {
        View myview = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,myview).create();
        alertDialog.show();
        addOrderViewModel.addOrder(systemDataLocal.getLoginData().getId_users(),String.valueOf(ongkir)).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    alertDialog.dismiss();
                    startActivity(new Intent(CheckoutActivity.this, ConfirmActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }
        });
    }
}