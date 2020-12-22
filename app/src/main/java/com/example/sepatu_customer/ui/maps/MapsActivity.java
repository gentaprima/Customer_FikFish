package com.example.sepatu_customer.ui.maps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.utils.DialogClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String all_address;
    private static final int CODE_PERMISSION_GPS = 1;
    boolean isGpsEnabled;
    private boolean pick;
    private LocationManager lm;
    private SystemDataLocal systemDataLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null){
             mapFragment.getMapAsync(this);
        }
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Intent intent = getIntent();
        pick = intent.getBooleanExtra("pick",false);
        systemDataLocal = new SystemDataLocal(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_PERMISSION_GPS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }


    public void onMapReady(final GoogleMap googleMap) {
       mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_PERMISSION_GPS);
            } else {
                if (isGpsEnabled) {
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    mMap.setMyLocationEnabled(true);
                    if (location != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
                    }
                }
            }
        }

       if(pick){
           final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
           mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
               @Override
               public void onMapClick(LatLng latLng) {
                   List<Address> addresses;
                   mMap.clear();
                   mMap.addMarker(new MarkerOptions().position(latLng));
                   googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                   try {
                       addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                       String address = addresses.get(0).getAddressLine(0);
                       String city = addresses.get(0).getSubAdminArea();
                       String post_code = addresses.get(0).getPostalCode();
                       String kecamatan = addresses.get(0).getLocality();
                       String provinsi = addresses.get(0).getAdminArea();
                       all_address = address + " " + city + " ";
                       String latitude = String.valueOf(latLng.latitude);
                       String longtitude = String.valueOf(latLng.longitude);
                       systemDataLocal.setCoordinate(all_address,latitude,longtitude,post_code,kecamatan,city,provinsi,"changed");
                   } catch (IOException e) {
                       e.printStackTrace();

                   }
                   alertDialogYesOrNo(all_address);
               }
           });
       }
    }

    private void alertDialogYesOrNo(String all_address) {
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.alert_location_yes, null, false);
        EditText edtAddress = v.findViewById(R.id.edtAddress);
        AlertDialog.Builder builder = DialogClass.dialog(this, v);
        builder.setTitle("Simpan Alamat ?");
        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        edtAddress.setText(all_address);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(lm != null){
            isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!isGpsEnabled){
                alertDialogLocation();
            }else{
                if(mMap != null){
                    mMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    private void alertDialogLocation() {
        AlertDialog.Builder builder = DialogClass.dialog(this,null);
        builder.setTitle("Required Permission");
        builder.setMessage("Aktifkan Location Service");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setGpsEnabled();
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
    }

    private void setGpsEnabled() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}