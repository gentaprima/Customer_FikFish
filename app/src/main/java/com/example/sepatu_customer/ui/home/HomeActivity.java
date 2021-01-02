package com.example.sepatu_customer.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.cart.CartActivity;
import com.example.sepatu_customer.ui.fragment.AccountFragment;
import com.example.sepatu_customer.ui.fragment.HomeFragment;
import com.example.sepatu_customer.ui.fragment.LikeFragment;
import com.example.sepatu_customer.ui.fragment.TransactionFragment;
import com.example.sepatu_customer.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class HomeActivity extends AppCompatActivity implements AccountFragment.OnCallbackReceived {

    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentLike = new LikeFragment();
    final Fragment fragmentAccount = new AccountFragment();
    final Fragment fragmentTransaction = new TransactionFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentHome;
    BottomNavigationView navigationView;
    FrameLayout containerFragment;
    TextView title;
    SystemDataLocal systemDataLocal;

    ImageView imageCart;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.nav_view);
        containerFragment = findViewById(R.id.containerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.title);
        imageCart = findViewById(R.id.iv_cart);
        imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });



        if(getSupportActionBar() != null ){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        systemDataLocal = new SystemDataLocal(this);

        if(savedInstanceState != null){
            switch (savedInstanceState.getInt("fragsate")){
                case R.id.menu_home:
                    active = fragmentHome;
                    fm.beginTransaction().add(R.id.containerView,fragmentAccount,fragmentAccount.getClass().getSimpleName()).hide(fragmentAccount).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentLike,fragmentLike.getClass().getSimpleName()).hide(fragmentLike).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentTransaction,fragmentTransaction.getClass().getSimpleName()).hide(fragmentTransaction).commit();
                    break;

                case R.id.menu_like:
                    active = fragmentLike;
                    fm.beginTransaction().add(R.id.containerView,fragmentAccount,fragmentAccount.getClass().getSimpleName()).hide(fragmentAccount).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentHome,fragmentHome.getClass().getSimpleName()).hide(fragmentHome).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentTransaction,fragmentTransaction.getClass().getSimpleName()).hide(fragmentTransaction).commit();
                    break;

                case R.id.menu_transaksi:
                    active = fragmentTransaction;
                    fm.beginTransaction().add(R.id.containerView,fragmentAccount,fragmentAccount.getClass().getSimpleName()).hide(fragmentAccount).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentHome,fragmentHome.getClass().getSimpleName()).hide(fragmentHome).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentLike,fragmentLike.getClass().getSimpleName()).hide(fragmentLike).commit();
                    break;

                case R.id.menu_account:
                    active = fragmentAccount;
                    fm.beginTransaction().add(R.id.containerView,fragmentTransaction,fragmentTransaction.getClass().getSimpleName()).hide(fragmentTransaction).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentHome,fragmentHome.getClass().getSimpleName()).hide(fragmentHome).commit();
                    fm.beginTransaction().add(R.id.containerView,fragmentLike,fragmentLike.getClass().getSimpleName()).hide(fragmentLike).commit();
                    break;
            }
            fm.beginTransaction().add(R.id.containerView,active,active.getClass().getSimpleName()).commit();
        }else{
            fm.beginTransaction().add(R.id.containerView,fragmentAccount,fragmentAccount.getClass().getSimpleName()).hide(fragmentAccount).commit();
            fm.beginTransaction().add(R.id.containerView,fragmentLike,fragmentLike.getClass().getSimpleName()).hide(fragmentLike).commit();
            fm.beginTransaction().add(R.id.containerView,fragmentTransaction,fragmentTransaction.getClass().getSimpleName()).hide(fragmentTransaction).commit();
            fm.beginTransaction().add(R.id.containerView,fragmentHome,fragmentHome.getClass().getSimpleName()).commit();
        }

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        fm.beginTransaction().hide(active).show(fragmentHome).commit();
                        active = fragmentHome;
                        title.setText("Home");
                        imageCart.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.menu_like:
                        fm.beginTransaction().hide(active).show(fragmentLike).commit();
                        active = fragmentLike;
                        title.setText("Favorite");
                        imageCart.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.menu_transaksi:
                        fm.beginTransaction().hide(active).show(fragmentTransaction).commit();
                        active = fragmentTransaction;
                        title.setText("Riwayat Pemesanan");
                        imageCart.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.menu_account:
                        if(systemDataLocal.getCheckLogin()){
                             fm.beginTransaction().hide(active).show(fragmentAccount).commit();
                        }else{
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finish();
                        }
                        imageCart.setVisibility(View.GONE);
                        active = fragmentAccount;
                        title.setText("Akun Saya");
                        return true;

                    default:    return false;
                }


            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("NotifApps", "NotifyApps", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        getCurrentFireBaseToken();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt("fragstate",navigationView.getSelectedItemId());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void getCurrentFireBaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("currentToken", token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
//                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        System.out.println(msg);
                    }
                });
    }

    @Override
    public void Update() {
        Toast.makeText(getApplicationContext(),"Anda Telah logout !",Toast.LENGTH_LONG).show();
        fm.beginTransaction().hide(active).show(fragmentHome).commit();
        navigationView.setSelectedItemId(R.id.menu_home);
        active = fragmentHome;
        title.setText("Home");
        imageCart.setVisibility(View.VISIBLE);
        systemDataLocal.destroySession();
    }
}