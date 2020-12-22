package com.example.sepatu_customer.ui.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.ui.login.LoginActivity;
import com.example.sepatu_customer.utils.DialogClass;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_login;
    private RegisterViewModel registerViewModel;
    EditText edt_username,edt_fullname,edt_password,edt_email;
    Button btn_register;
    private android.app.AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_login = findViewById(R.id.tv_login);
        btn_register = findViewById(R.id.btn_register);
        edt_username = findViewById(R.id.edt_username);
        edt_fullname = findViewById(R.id.edt_fullname);
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        tv_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.grey)); //status bar or the time bar at the top
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;

            case R.id.btn_register:
                register();
                break;
        }

    }

    private void register() {
        String username = edt_username.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        String full_name = edt_fullname.getText().toString();
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();

        registerViewModel.getRegisterResponse(username,full_name,password,email).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly != null){
                    if(messageOnly.getStatus()){
                        alertDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }else{
                        alertDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    alertDialog.dismiss();

                }
            }
        });
    }
}