package com.example.sepatu_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.cart.DataCart;
import com.example.sepatu_customer.network.Constanta;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private Context context;
    private List<DataCart> listCheckout;

    public CheckoutAdapter(Context context, List<DataCart> listCheckout) {
        this.context = context;
        this.listCheckout = listCheckout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.container_checkout,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataCart dataCart = listCheckout.get(position);
        holder.tv_nama.setText(dataCart.getNamaIkan());
        holder.tv_qty.setText(dataCart.getQuantity() + "x");
        int total = Integer.parseInt(dataCart.getHarga()) * Integer.parseInt(dataCart.getQuantity());
        holder.tv_harga.setText("Rp " + String.format("%,d",total));
        Glide.with(context).load(Constanta.BASE_URL_IMG + dataCart.getFoto()).into(holder.imageProduct);
    }

    @Override
    public int getItemCount() {
        return listCheckout.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView tv_harga,tv_nama,tv_qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_qty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
