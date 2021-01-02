package com.example.sepatu_customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.order.OrderData;
import com.example.sepatu_customer.network.Constanta;
import com.example.sepatu_customer.ui.order.DetailOrderActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderData> listOrder;

    public OrderAdapter(Context context, List<OrderData> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.container_order,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OrderData dataOrder = listOrder.get(position);
        holder.tv_orderid.setText(dataOrder.getOrderId());
        holder.tv_tgl.setText(dataOrder.getDate());
        holder.tv_status.setText(dataOrder.getStatus());
        Glide.with(context).load(Constanta.BASE_URL_IMG + dataOrder.getFoto()).into(holder.imageProduct);
        holder.cardOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToDetail = new Intent(context, DetailOrderActivity.class);
                moveToDetail.putExtra("data",dataOrder);
                context.startActivity(moveToDetail);
            }
        });

        if(dataOrder.getStatus().equals("Ditunda")){
            holder.tv_status.setBackgroundResource(R.drawable.bg_outline_danger);
            holder.tv_status.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
        }else if(dataOrder.getStatus().equals("Menunggu Pengiriman")){
            holder.tv_status.setBackgroundResource(R.drawable.bg_outline_warning);
            holder.tv_status.setTextColor(ContextCompat.getColor(context,R.color.colorOrange));
        }else if(dataOrder.getStatus().equals("Sudah Diterima")){
            holder.tv_status.setBackgroundResource(R.drawable.bg_outline_success);
            holder.tv_status.setTextColor(ContextCompat.getColor(context,R.color.colorGreen));
        }else{
            holder.tv_status.setBackgroundResource(R.drawable.bg_button);
            holder.tv_status.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
        }
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView tv_tgl,tv_orderid,tv_status;
        CardView cardOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            tv_orderid = itemView.findViewById(R.id.tv_orderid);
            tv_tgl = itemView.findViewById(R.id.tv_tgl);
            tv_status = itemView.findViewById(R.id.tv_status);
            cardOrder = itemView.findViewById(R.id.cardOrder);
        }
    }
}
