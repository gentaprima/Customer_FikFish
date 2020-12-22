package com.example.sepatu_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.product.ProductEntitity;
import com.example.sepatu_customer.network.Constanta;
import com.example.sepatu_customer.ui.product.ProductDetailActivity;

import java.text.DecimalFormat;
import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {

    private Context context;
    private List<ProductEntitity> listProduct;
    private OnItemClickCallBack onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallBack onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public ProductCategoryAdapter(Context context, List<ProductEntitity> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_product_category,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductEntitity productEntitity = listProduct.get(position);
        holder.tv_harga.setText("Rp " + String.format("%,d",Integer.parseInt(productEntitity.getHarga())));
        Glide.with(context).load(Constanta.BASE_URL_IMG + productEntitity.getFoto()).into(holder.imageProduct);
        holder.imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveDetail = new Intent(context, ProductDetailActivity.class);
                moveDetail.putExtra("data",productEntitity);
                context.startActivity(moveDetail);
            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(productEntitity.getIdIkan());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_harga,btn_add;
        ImageView imageProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            btn_add = itemView.findViewById(R.id.btn_add);
        }
    }
    public interface OnItemClickCallBack{
        void onItemClicked(String id_product);
    }
}
