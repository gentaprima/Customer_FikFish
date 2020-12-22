package com.example.sepatu_customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.product.ProductEntitity;
import com.example.sepatu_customer.network.Constanta;
import com.example.sepatu_customer.ui.product.ProductDetailActivity;

import java.util.List;

public class ProductAdapterSlider extends RecyclerView.Adapter<ProductAdapterSlider.ViewHolder> {

    private Context context;
    private List<ProductEntitity> listProduct;

    public ProductAdapterSlider(Context context, List<ProductEntitity> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductEntitity productEntitity = listProduct.get(position);
        holder.tv_nama.setText(productEntitity.getNamaIkan());
        holder.buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, ProductDetailActivity.class);
                detail.putExtra("data",productEntitity);
                context.startActivity(detail);
            }
        });

        Glide.with(context).load(Constanta.BASE_URL_IMG + productEntitity.getFoto()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        ImageView imageView;
        Button buttonBuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            imageView = itemView.findViewById(R.id.imageView);
            buttonBuy = itemView.findViewById(R.id.buttonBuy);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
