package com.example.sepatu_customer.ui.confirm_order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.ui.home.HomeActivity;

public class ConfirmActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv_title;
    ImageView iv_cart;
    private Button btnsubmit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        toolbar  = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.title);
        btnsubmit = findViewById(R.id.btn_submit);
        iv_cart = findViewById(R.id.iv_cart);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tv_title.setText("Confirmation");
        toolbar.setNavigationIcon(R.drawable.ic_back_button_black);
        iv_cart.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmActivity.this, HomeActivity.class));
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}