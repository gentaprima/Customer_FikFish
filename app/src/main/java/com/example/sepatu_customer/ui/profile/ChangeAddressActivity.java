package com.example.sepatu_customer.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.maps.MapsActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ChangeAddressActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private TextView tv_title,tv_set_loc,tv_detail_alamat,tv_set,tv_ubah;
    private SystemDataLocal systemDataLocal;
    private EditText edt_namapenerima,edt_phone,edt_pos,edt_addres;
    private ImageView iv_cart,iv_map;
    private LinearLayout btn_maps;
    private Button btn_submit;
    private ChangeAddressViewModel changeAddressViewModel;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.title);
        edt_namapenerima = findViewById(R.id.edt_nama_penerima);
        edt_phone = findViewById(R.id.edt_phone);
        btn_maps = findViewById(R.id.btn_maps);
        edt_addres = findViewById(R.id.edt_alamat);
        edt_pos  = findViewById(R.id.edt_pos);
        tv_set_loc = findViewById(R.id.textView2);
        iv_map = findViewById(R.id.iv_map);
        tv_set = findViewById(R.id.tv_set);
        tv_detail_alamat = findViewById(R.id.tv_detail_alamat);
        tv_ubah = findViewById(R.id.tv_ubah);
        changeAddressViewModel = ViewModelProviders.of(this).get(ChangeAddressViewModel.class);
        btn_submit = findViewById(R.id.btn_submit);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setVisibility(View.GONE);
        systemDataLocal = new SystemDataLocal(this);
        edt_namapenerima.setText(systemDataLocal.getLoginData().getFull_name());
        edt_phone.setText(systemDataLocal.getLoginData().getNo_hp());
        toolbar.setNavigationIcon(R.drawable.ic_back_button_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                systemDataLocal.destroyCoordinate();
            }
        });
        tv_title.setText("Ganti Alamat Pengiriman");
        btn_maps.setOnClickListener(this);
        tv_ubah.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        if(!systemDataLocal.getLoginData().getAlamat().equals("")){
            double latitude = Double.parseDouble(systemDataLocal.getLoginData().getLatitude());
            double longitude = Double.parseDouble(systemDataLocal.getLoginData().getLongtitude());
            final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(latitude,longitude,1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getSubAdminArea();
                String post_code = addresses.get(0).getPostalCode();
                String kecamatan = addresses.get(0).getLocality();
                String provinsi = addresses.get(0).getAdminArea();
                tv_set_loc.setVisibility(View.GONE);
                tv_detail_alamat.setVisibility(View.VISIBLE);
                tv_detail_alamat.setText(kecamatan + ", " + city + ", " + provinsi);
                iv_map.setVisibility(View.VISIBLE);
                tv_set.setVisibility(View.GONE);
                btn_maps.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tv_ubah.setVisibility(View.VISIBLE);
                edt_pos.setText(post_code);
                edt_addres.setText(systemDataLocal.getLoginData().getAlamat());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_maps:
                goToMaps();
                break;

            case R.id.tv_ubah:
                goToMaps();
                break;

            case R.id.btn_submit:
                changeAddress();
                break;
        }
    }

    private void changeAddress() {
        String changed = systemDataLocal.getCoordinate().getString("changed","");
        String id_users = systemDataLocal.getLoginData().getId_users();
        String latitude = "";
        String longitude = "";
        String alamat = "";
        if(changed != null && !changed.isEmpty()){
             latitude = systemDataLocal.getCoordinate().getString("latitude","");
             longitude = systemDataLocal.getCoordinate().getString("longtitude","");
             alamat = systemDataLocal.getCoordinate().getString("addr","");
        }else{
             latitude = systemDataLocal.getLoginData().getLatitude();
             longitude = systemDataLocal.getLoginData().getLongtitude();
             alamat = systemDataLocal.getLoginData().getAlamat();
        }
        System.out.println(latitude);
        systemDataLocal.editAllSessionLogin(id_users,
                                            systemDataLocal.getLoginData().getUsername(),
                                            systemDataLocal.getLoginData().getFull_name(),
                                            systemDataLocal.getLoginData().getPassword(),
                                            systemDataLocal.getLoginData().getEmail(),
                                            systemDataLocal.getLoginData().getRole(),
                                            systemDataLocal.getLoginData().getTgl_lahir(),
                                            systemDataLocal.getLoginData().getJenis_kelamin(),
                                            alamat,
                                            latitude,
                                            longitude,
                                            systemDataLocal.getLoginData().getPhoto(),
                                            systemDataLocal.getLoginData().getNo_hp());
        changeAddressViewModel.updateAddress(id_users,alamat,latitude,longitude).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    onBackPressed();
                    systemDataLocal.destroyCoordinate();
                }else{
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void goToMaps() {
        Intent intent = new Intent(ChangeAddressActivity.this, MapsActivity.class);
        intent.putExtra("pick",true);
        startActivity(intent);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        String changed = systemDataLocal.getCoordinate().getString("changed","");
        String kecamatan = systemDataLocal.getCoordinate().getString("kecamatan","");
        String kota = systemDataLocal.getCoordinate().getString("kota","");
        String provinsi = systemDataLocal.getCoordinate().getString("provinsi","");
        if (changed != null && !changed.isEmpty()) {
            tv_set_loc.setVisibility(View.GONE);
            tv_detail_alamat.setVisibility(View.VISIBLE);
            tv_detail_alamat.setText(kecamatan + ", " + kota + ", " + provinsi);
            iv_map.setVisibility(View.VISIBLE);
            tv_set.setVisibility(View.GONE);
            btn_maps.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            tv_ubah.setVisibility(View.VISIBLE);
            edt_pos.setText(systemDataLocal.getCoordinate().getString("post_code",""));
            edt_addres.setText(systemDataLocal.getCoordinate().getString("addr",""));

        }
    }
}