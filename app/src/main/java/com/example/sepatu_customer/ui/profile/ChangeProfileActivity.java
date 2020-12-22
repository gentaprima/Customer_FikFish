package com.example.sepatu_customer.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.utils.DialogClass;

public class ChangeProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv_title;
    ImageView iv_cart;
    SystemDataLocal systemDataLocal;
    Button btn_submit;
    private ChangeProfileViewModel changeProfileViewModel;
    private android.app.AlertDialog alertDialog;
    String id_users;

    EditText edt_username,edt_full_name,edt_phone;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.title);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setVisibility(View.GONE);
        edt_username = findViewById(R.id.edt_username);
        edt_full_name = findViewById(R.id.edt_fullname);
        edt_phone = findViewById(R.id.edt_phone);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        btn_submit = findViewById(R.id.btn_submit);
        systemDataLocal = new SystemDataLocal(this);
        changeProfileViewModel = ViewModelProviders.of(this).get(ChangeProfileViewModel.class);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        toolbar.setNavigationIcon(R.drawable.ic_back_button_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title.setText("Profile");
        edt_username.setText(systemDataLocal.getLoginData().getUsername());
        edt_full_name.setText(systemDataLocal.getLoginData().getFull_name());
        edt_phone.setText(systemDataLocal.getLoginData().getNo_hp());
        id_users = systemDataLocal.getLoginData().getId_users();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    private void updateProfile() {
        String username = edt_username.getText().toString();
        String full_name = edt_full_name.getText().toString();
        String phone = edt_phone.getText().toString();
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();
        systemDataLocal.editAllSessionLogin(systemDataLocal.getLoginData().getId_users(),
                                            username,
                                            full_name,
                                            systemDataLocal.getLoginData().getPassword(),
                                            systemDataLocal.getLoginData().getEmail(),
                                            systemDataLocal.getLoginData().getRole(),
                                            systemDataLocal.getLoginData().getTgl_lahir(),
                                            systemDataLocal.getLoginData().getJenis_kelamin(),
                                            systemDataLocal.getLoginData().getAlamat(),
                                            systemDataLocal.getLoginData().getLatitude(),
                                            systemDataLocal.getLoginData().getLongtitude(),
                                            systemDataLocal.getLoginData().getPhoto(),
                                            phone);
        changeProfileViewModel.updateProfileUsers(id_users,username,full_name,phone).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}