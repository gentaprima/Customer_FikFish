package com.example.sepatu_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.product.ProductEntitity;
import com.example.sepatu_customer.model.wishlist.ProductWishlist;
import com.example.sepatu_customer.network.Constanta;
import com.example.sepatu_customer.ui.product.ProductDetailActivity;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private Context context;
    private List<ProductWishlist> listProduct;
    private ProductCategoryAdapter.OnItemClickCallBack onItemClickCallback;

    public void setOnItemClickCallback(ProductCategoryAdapter.OnItemClickCallBack onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }


    public WishlistAdapter(Context context, List<ProductWishlist> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_product_wishlist,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductWishlist productWishlist = listProduct.get(position);
        holder.tv_harga.setText("Rp " + String.format("%,d",Integer.parseInt(productWishlist.getHarga())));
        Glide.with(context).load(Constanta.BASE_URL_IMG + productWishlist.getFoto()).into(holder.imageProduct);
//        holder.imageProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moveDetail = new Intent(context, ProductDetailActivity.class);
//                moveDetail.putExtra("data",productWishlist);
//                context.startActivity(moveDetail);
//            }
//        });
        holder.imageLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(productWishlist.getIdProduct());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_harga;
        ImageView imageProduct,imageLove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            imageLove = itemView.findViewById(R.id.iv_favorite);
        }
    }
    public interface OnItemClickCallBack{
        void onItemClicked(String id_product);
    }
}
