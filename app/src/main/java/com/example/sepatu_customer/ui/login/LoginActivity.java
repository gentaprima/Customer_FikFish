package com.example.sepatu_customer.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.model.login.LoginResponse;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.home.HomeActivity;
import com.example.sepatu_customer.ui.forgetpass.ForgetPassActivity;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.ui.Register.RegisterActivity;
import com.example.sepatu_customer.utils.DialogClass;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_sign,tv_forget;
    EditText edt_username,edt_password;
    Button btn_login;
    private android.app.AlertDialog alertDialog;
    private LoginViewModel loginViewModel;
    private SystemDataLocal systemDataLocal;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_sign = findViewById(R.id.tv_sign);
        tv_forget = findViewById(R.id.tv_forget);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);

        tv_sign.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        systemDataLocal = new SystemDataLocal(this);

        if(systemDataLocal.getCheckLogin()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.grey)); //status bar or the time bar at the top
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sign:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
                break;

            case R.id.btn_login:
                login();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
    }

    private void login() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);

        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();

        loginViewModel.getLoginResponse(username,password).observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if(loginResponse != null){
                    if(loginResponse.getStatus()){
                        alertDialog.dismiss();
//                        Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_LONG).show();
                        systemDataLocal = new SystemDataLocal(LoginActivity.this,loginResponse.getUsersData());
                        systemDataLocal.setSessionLogin();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }else{
                        alertDialog.dismiss();
                        Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    alertDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        systemDataLocal = new SystemDataLocal(this);
        if(systemDataLocal.getCheckLogin()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }
}