package com.example.sepatu_customer.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.adapter.OrderAdapter;
import com.example.sepatu_customer.model.order.OrderResponse;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.order.GetDataOrderViewModel;

public class TransactionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rv_order;
    private SystemDataLocal systemDataLocal;
    private GetDataOrderViewModel getDataOrderViewModel;
    private Activity activity;
    ImageView iv_notif;
    TextView tv_notif;
    SwipeRefreshLayout refreshLayout;
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_order = view.findViewById(R.id.rv_order);
        systemDataLocal = new SystemDataLocal(getContext());
        getDataOrderViewModel = ViewModelProviders.of(this).get(GetDataOrderViewModel.class);
        iv_notif = view.findViewById(R.id.iv_notif);
        tv_notif = view.findViewById(R.id.tv_notif);
        refreshLayout = view.findViewById(R.id.refreshLayout);
//        if(!systemDataLocal.getLoginData().getId_users().equals("")){
//
//        }else{
//
//        }

        if(systemDataLocal.getCheckLogin()){
            loadData();
            tv_notif.setVisibility(View.VISIBLE);
            iv_notif.setVisibility(View.VISIBLE);
        }else{
            tv_notif.setVisibility(View.GONE);
            iv_notif.setVisibility(View.GONE);
            tv_notif.setText("Silahkan Login untuk melihat Daftar Transaksi Anda");
        }


        refreshLayout.setOnRefreshListener(this);

    }

    private void loadData() {
        String id_users = systemDataLocal.getLoginData().getId_users();
        getDataOrderViewModel.getDataOrder(id_users).observe(this, new Observer<OrderResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(OrderResponse orderResponse) {
                if(orderResponse.getData().size() > 0){
                    OrderAdapter orderAdapter = new OrderAdapter(activity,orderResponse.getData());
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    rv_order.setAdapter(orderAdapter);
                    rv_order.setLayoutManager(lm);
                    rv_order.setVisibility(View.VISIBLE);
                    tv_notif.setVisibility(View.GONE);
                    iv_notif.setVisibility(View.GONE);
                }else{
                    rv_order.setVisibility(View.GONE);
                    tv_notif.setVisibility(View.VISIBLE);
                    iv_notif.setVisibility(View.VISIBLE);
                    tv_notif.setText("Daftar Pesanan anda masih kosong !");
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        if(systemDataLocal.getCheckLogin()){
            loadData();
        }else{
            rv_order.setVisibility(View.GONE);
            tv_notif.setVisibility(View.VISIBLE);
            iv_notif.setVisibility(View.VISIBLE);
            tv_notif.setText("Silahkan Login untuk melihat daftar pesanan anda");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                refreshLayout.setRefreshing(false);
            }
        },1000);
    }
}