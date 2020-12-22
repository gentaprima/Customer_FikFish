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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<DataCart> listProduct;
    private CartAdapter.OnItemClickCallBack onItemClickCallback;

    public void setOnItemClickCallback(CartAdapter.OnItemClickCallBack onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public CartAdapter(Context context, List<DataCart> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_cart,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataCart dataCart = listProduct.get(position);
        holder.tv_nama.setText(dataCart.getNamaIkan());
        holder.tv_qty.setText(dataCart.getQuantity());
        int total = Integer.parseInt(dataCart.getHarga()) * Integer.parseInt(dataCart.getQuantity());
        holder.tv_harga.setText("Rp " + String.format("%,d",total));
        Glide.with(context).load(Constanta.BASE_URL_IMG + dataCart.getFoto()).into(holder.imageProduct);
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(dataCart.getIdCart());
            }
        });

        holder.tv_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClickedMin(dataCart.getIdCart());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView tv_harga,tv_nama,tv_qty,tv_plus,tv_min;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_qty = itemView.findViewById(R.id.qty);
            tv_plus = itemView.findViewById(R.id.tv_plus);
            tv_min = itemView.findViewById(R.id.tv_min);

        }
    }
    public interface OnItemClickCallBack{
        void onItemClicked(String id_cart);
        void onItemClickedMin(String id_cart);
    }
}
