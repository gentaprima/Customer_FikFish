package com.example.sepatu_customer.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.adapter.ProductAdapterSlider;
import com.example.sepatu_customer.adapter.ProductCategoryAdapter;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.category.CategoryResponse;
import com.example.sepatu_customer.model.product.ProductResponse;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.cart.AddCartViewModel;
import com.example.sepatu_customer.ui.home.CategoryHomeViewModel;
import com.example.sepatu_customer.ui.home.ProductCategoryViewModel;
import com.example.sepatu_customer.ui.home.ProductHomeViewModel;
import com.example.sepatu_customer.utils.DialogClass;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ProductHomeViewModel productHomeViewModel;
    ViewPager2 sliderProduct;
    private Context context;
    private Activity activity;
    ProgressBar loading;
    private CategoryHomeViewModel categoryHomeViewModel;
    TextView tv_all,tv_hias,tv_predator;
    private String category = "";
    private ProductCategoryViewModel productCategoryViewModel;
    RecyclerView rv_product;
    private AddCartViewModel addCartViewModel;
    String id_users;
    private SystemDataLocal systemDataLocal;
    private android.app.AlertDialog alertDialog;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderProduct = view.findViewById(R.id.slider_product);
        productHomeViewModel = ViewModelProviders.of(this).get(ProductHomeViewModel.class);
        categoryHomeViewModel = ViewModelProviders.of(this).get(CategoryHomeViewModel.class);
        productCategoryViewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel.class);
        addCartViewModel = ViewModelProviders.of(this).get(AddCartViewModel.class);
        tv_all = view.findViewById(R.id.tv_all);
        tv_hias = view.findViewById(R.id.tv_hias);
        tv_predator = view.findViewById(R.id.tv_predator);
        rv_product = view.findViewById(R.id.rv_product);
        systemDataLocal = new SystemDataLocal(getContext());
        id_users = systemDataLocal.getLoginData().getId_users();
        loadData();
        loadDataProductByCategory();


        tv_all.setOnClickListener(this);
        tv_hias.setOnClickListener(this);
        tv_predator.setOnClickListener(this);



    }

    private void loadDataProductByCategory() {
        productCategoryViewModel.getResponseProduct(category).observe(this, new Observer<ProductResponse>() {
            @Override
            public void onChanged(ProductResponse productResponse) {
                if(productResponse != null){
                    if(productResponse.getDataProduct().size() > 0){
                        final ProductCategoryAdapter productCategoryAdapter = new ProductCategoryAdapter(activity,productResponse.getDataProduct());
                        rv_product.setLayoutManager(new GridLayoutManager(context,2));
                        rv_product.setAdapter(productCategoryAdapter);
                        rv_product.setVisibility(View.VISIBLE);
                        productCategoryAdapter.setOnItemClickCallback(new ProductCategoryAdapter.OnItemClickCallBack() {
                            @Override
                            public void onItemClicked(String id_product) {
                                addCart(id_product);
                            }
                        });
                    }else{
                        rv_product.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public void addCart(String id_product){
        View myview = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(getContext(),myview).create();
        alertDialog.show();
        if(!id_users.equals("")){
            addCartViewModel.addCart(id_product,id_users).observe(this, new Observer<MessageOnly>() {
                @Override
                public void onChanged(MessageOnly messageOnly) {
                    if(messageOnly.getStatus()){
                        Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }else{
                        Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(),"Silahkan login terlebih dahulu ...",Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        loadDataProductByCategory();
    }

    private void loadData() {
        productHomeViewModel.getResponseProduct().observe(this, new Observer<ProductResponse>() {
            @Override
            public void onChanged(ProductResponse productResponse) {
                if(productResponse != null){
                    if(productResponse.getDataProduct().size() > 0){
                        if(activity == null){
                            return;
                        }
                        sliderProduct.setAdapter(new ProductAdapterSlider(activity,productResponse.getDataProduct()));
                        sliderProduct.setClipToPadding(false);
                        sliderProduct.setClipChildren(false);
                        sliderProduct.setOffscreenPageLimit(3);
                        sliderProduct.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


                        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                            @Override
                            public void transformPage(@NonNull View page, float position) {
                                float r = 1 - Math.abs(position);
                                page.setScaleY(0.95f + r * 0.1f);
                            }
                        });

                        sliderProduct.setPageTransformer(compositePageTransformer);
                    }
                }
            }
        });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_all:
                tv_all.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_hias.setTextColor(getResources().getColor(R.color.black));
                tv_predator.setTextColor(getResources().getColor(R.color.black));
                category = "";
                loadDataProductByCategory();
                break;

            case R.id.tv_hias:
                tv_all.setTextColor(getResources().getColor(R.color.black));
                tv_hias.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_predator.setTextColor(getResources().getColor(R.color.black));
                category = "5";
                loadDataProductByCategory();
                break;

            case R.id.tv_predator:
                tv_all.setTextColor(getResources().getColor(R.color.black));
                tv_hias.setTextColor(getResources().getColor(R.color.black));
                tv_predator.setTextColor(getResources().getColor(R.color.colorPrimary));
                category = "6";
                loadDataProductByCategory();
                break;

        }
    }
}