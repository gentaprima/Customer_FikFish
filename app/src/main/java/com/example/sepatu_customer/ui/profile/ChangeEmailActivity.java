package com.example.sepatu_customer.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
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

public class ChangeEmailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edt_oldemail,edt_newemail,edt_konfirm;
    Button btnSubmit;
    private SystemDataLocal systemDataLocal;
    private ChangeEmailViewModel changeEmailViewModel;
    private android.app.AlertDialog alertDialog;
    ImageView iv_cart;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        edt_newemail = findViewById(R.id.edt_newemail);
        edt_oldemail = findViewById(R.id.edt_oldemail);
        edt_konfirm = findViewById(R.id.edt_konfirm);
        btnSubmit = findViewById(R.id.btn_submit);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv_title = findViewById(R.id.title);
        tv_title.setText("Form Ubah Email");
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

        changeEmailViewModel = ViewModelProviders.of(this).get(ChangeEmailViewModel.class);
        btnSubmit.setOnClickListener(this);
        systemDataLocal = new SystemDataLocal(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_submit){
            changeEmail();
        }
    }

    private void changeEmail() {
        String oldEmail = edt_oldemail.getText().toString();
        final String newEmail = edt_newemail.getText().toString();
        String confirmEmail = edt_konfirm.getText().toString();
        String idUsers = systemDataLocal.getLoginData().getId_users();

        View myview = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,myview).create();
        alertDialog.show();
        changeEmailViewModel.changeEmail(oldEmail,newEmail,confirmEmail,idUsers).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    systemDataLocal.editAllSessionLogin(systemDataLocal.getLoginData().getId_users(),
                            systemDataLocal.getLoginData().getUsername(),
                            systemDataLocal.getLoginData().getFull_name(),
                            systemDataLocal.getLoginData().getPassword(),
                            newEmail,
                            systemDataLocal.getLoginData().getRole(),
                            systemDataLocal.getLoginData().getTgl_lahir(),
                            systemDataLocal.getLoginData().getJenis_kelamin(),
                            systemDataLocal.getLoginData().getAlamat(),
                            systemDataLocal.getLoginData().getLatitude(),
                            systemDataLocal.getLoginData().getLongtitude(),
                            systemDataLocal.getLoginData().getPhoto(),
                            systemDataLocal.getLoginData().getNo_hp());
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}