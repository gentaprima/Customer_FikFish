package com.example.sepatu_customer.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

public class ChangePassword extends AppCompatActivity {

    EditText edt_oldpassword,edt_newpassword,edt_confirm;
    Button btn_submit;
    Toolbar toolbar;
    TextView tv_title;
    private ChangePasswordViewModel changePasswordViewModel;
    private SystemDataLocal systemDataLocal;
    private android.app.AlertDialog alertDialog;
    String new_password;
    ImageView iv_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edt_oldpassword = findViewById(R.id.edt_oldpassword);
        edt_newpassword = findViewById(R.id.edt_newpassword);
        edt_confirm = findViewById(R.id.edt_confirm);
        btn_submit = findViewById(R.id.btn_submit);
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.title);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        systemDataLocal = new SystemDataLocal(this);
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        toolbar.setNavigationIcon(R.drawable.ic_back_button_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title.setText("Ganti Password");
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });


    }

    private void updatePassword() {
        String old_password = edt_oldpassword.getText().toString();
        new_password = edt_newpassword.getText().toString();
        String confirm_password = edt_confirm.getText().toString();
        String id_users = systemDataLocal.getLoginData().getId_users();
        View myview = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,myview).create();
        alertDialog.show();
        changePasswordViewModel.changePassword(id_users,old_password,new_password,confirm_password).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    systemDataLocal.editAllSessionLogin(systemDataLocal.getLoginData().getId_users(),
                            systemDataLocal.getLoginData().getUsername(),
                            systemDataLocal.getLoginData().getFull_name(),
                            new_password,
                            systemDataLocal.getLoginData().getEmail(),
                            systemDataLocal.getLoginData().getRole(),
                            systemDataLocal.getLoginData().getTgl_lahir(),
                            systemDataLocal.getLoginData().getJenis_kelamin(),
                            systemDataLocal.getLoginData().getAlamat(),
                            systemDataLocal.getLoginData().getLatitude(),
                            systemDataLocal.getLoginData().getLongtitude(),
                            systemDataLocal.getLoginData().getPhoto(),
                            systemDataLocal.getLoginData().getNo_hp());
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }
        });
    }
}