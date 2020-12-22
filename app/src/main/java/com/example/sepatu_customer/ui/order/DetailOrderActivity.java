package com.example.sepatu_customer.ui.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepatu_customer.R;
import com.example.sepatu_customer.adapter.DetailOrderAdapter;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.model.order.DataTransaction;
import com.example.sepatu_customer.model.order.OrderData;
import com.example.sepatu_customer.model.order.TransactionResponse;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.home.HomeActivity;
import com.example.sepatu_customer.ui.order.payment.AddPaymentViewModel;
import com.example.sepatu_customer.utils.DatePicker;
import com.example.sepatu_customer.utils.DialogClass;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.view.View.GONE;

public class DetailOrderActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView tv_title,tv_status,tv_totalharga,tv_idorder,tv_nama,tv_alamat,tv_phone,tv_ongkir,tv_biayatambahan,tv_pembayaran,tv_tunda;
    ImageView iv_cart,iv_infotagihanhide,iv_infotagihanshow,iv_alamatshow,iv_alamathide,imageGallery;;
    private OrderData orderData;
    CardView cardTagihan,cardAlamat,cardTunda,cardTunda2;
    private GetDetailDataOrderViewModel getDetailDataOrderViewModel;
    private RecyclerView rv_detail;
    private AddPaymentViewModel addPaymentViewModel;
    private final int REQUEST_PICK_PHOTO = 2;
    private final int REQUES_WRITE_PERMISION = 786;
    private String mediaPath;
    private String postPath;
    private android.app.AlertDialog alertDialog;
    Button btn_file;
    AlertDialog.Builder builder;
    View myview,dialogView;
    Button btn_gallery,btn_submit_file,btn_cancel,btn_tunda;
    private CancelOrderViewModel cancelOrderViewModel;
    private SystemDataLocal systemDataLocal;
    private ResumeShippingViewModel resumeShippingViewModel;
    EditText edt_tanggal;


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.title);
        tv_status = findViewById(R.id.tv_status);
        tv_totalharga = findViewById(R.id.tv_totalharga);
        tv_idorder = findViewById(R.id.tv_idorder);
        iv_alamatshow = findViewById(R.id.iv_alamat_show);
        iv_alamathide = findViewById(R.id.iv_alamat_hide);
        tv_nama = findViewById(R.id.tv_nama);
        tv_phone = findViewById(R.id.tv_phone);
        tv_alamat = findViewById(R.id.tv_alamat);
        btn_tunda = findViewById(R.id.btn_tunda);
        iv_cart  = findViewById(R.id.iv_cart);
        cardAlamat  = findViewById(R.id.cardAlamat);
        rv_detail  = findViewById(R.id.recyclerView);
        tv_ongkir  = findViewById(R.id.tv_ongkir);
        tv_pembayaran  = findViewById(R.id.tv_pembayaran);
        btn_file  = findViewById(R.id.btn_file);
        btn_cancel  = findViewById(R.id.btn_cancel);
        tv_tunda  = findViewById(R.id.tv_tunda);
        tv_biayatambahan  = findViewById(R.id.tv_biayatambahan);
        iv_infotagihanhide  = findViewById(R.id.iv_infotagihan_hide);
        iv_infotagihanshow  = findViewById(R.id.iv_infotagihan_show);
        cardTunda  = findViewById(R.id.cardTunda);
        cardTunda2  = findViewById(R.id.cardTunda2);
        systemDataLocal = new SystemDataLocal(this);
        setSupportActionBar(toolbar);
        tv_title.setText("Detail Pemesanan");
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        orderData = getIntent().getParcelableExtra("data");
        toolbar.setNavigationIcon(R.drawable.ic_back_button_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_cart.setVisibility(GONE);
        cardTagihan = findViewById(R.id.cardTagihan);
        iv_infotagihanhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardTagihan.setVisibility(GONE);
                iv_infotagihanhide.setVisibility(GONE);
                iv_infotagihanshow.setVisibility(View.VISIBLE);
            }
        });
        iv_infotagihanshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardTagihan.setVisibility(View.VISIBLE);
                iv_infotagihanshow.setVisibility(GONE);
                iv_infotagihanhide.setVisibility(View.VISIBLE);
            }
        });
        iv_alamatshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardAlamat.setVisibility(View.VISIBLE);
                iv_alamatshow.setVisibility(GONE);
                iv_alamathide.setVisibility(View.VISIBLE);
            }
        });
        iv_alamathide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardAlamat.setVisibility(GONE);
                iv_alamathide.setVisibility(GONE);
                iv_alamatshow.setVisibility(View.VISIBLE);
            }
        });
        tv_status.setText(orderData.getStatus());
        tv_idorder.setText(orderData.getOrderId());
        tv_phone.setText(orderData.getNoHp());
        tv_nama.setText(orderData.getFullName());
        tv_alamat.setText(systemDataLocal.getLoginData().getAlamat());
        tv_biayatambahan.setText("Rp " + String.format("%,d",Integer.parseInt(orderData.getAdditionalCosts())));
        tv_ongkir.setText("Rp " + String.format("%,d",Integer.parseInt(orderData.getShippingCosts())));
        getDetailDataOrderViewModel = ViewModelProviders.of(this).get(GetDetailDataOrderViewModel.class);
        resumeShippingViewModel = ViewModelProviders.of(this).get(ResumeShippingViewModel.class);
        String statusOrder = orderData.getStatus();
        if(statusOrder.equals("Menunggu Diproses")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pembayaran dapat dilakukan setelah pesanan di proses");
            btn_cancel.setVisibility(View.VISIBLE);
            cardTunda.setVisibility(GONE);
            cardTunda2.setVisibility(GONE);
        }else if(statusOrder.equals("Menunggu Pembayaran")){
            tv_pembayaran.setText("Silahkan Lampirkan Bukti Transfer Anda");
            btn_file.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
            cardTunda.setVisibility(GONE);
            cardTunda2.setVisibility(GONE);
        }else if(statusOrder.equals("Sedang Dikemas")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pesanan anda sedang dikemas, Terimakasih");
            cardTunda.setVisibility(GONE);
            cardTunda2.setVisibility(GONE);
        }else if(statusOrder.equals("Sedang Dikirim")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pesanan anda sedang dikirim, Terimakasih");
            cardTunda.setVisibility(GONE);
            cardTunda2.setVisibility(GONE);
        }else if(statusOrder.equals("Sedang Diproses")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Silahkan Tunggu Pembayaran Anda Sedang Kami Proses");
        }else if(statusOrder.equals("Sudah Diterima")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pesanan Anda Sudah diterima, Terimakasih");
            cardTunda.setVisibility(GONE);
            cardTunda2.setVisibility(GONE);
        }else if(statusOrder.equals("Ditunda")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pesanan Anda Sudah dibayar, Terimakasih");
            cardTunda.setVisibility(View.VISIBLE);
            cardTunda2.setVisibility(View.VISIBLE);
        }else if(statusOrder.equals("Dibatalkan")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pesanan Anda Sudah Dibatalkan");
            cardTunda.setVisibility(GONE);
            cardTunda2.setVisibility(GONE);
            btn_tunda.setVisibility(View.VISIBLE);
        }else if(statusOrder.equals("Menunggu Pengiriman")){
            btn_file.setVisibility(GONE);
            tv_pembayaran.setText("Pesanan Anda Sudah dibayar, Terimakasih");
            tv_tunda.setText("Pesanan anda akan dikirimkan berdasarkan tanggal yang anda tentukan");
            btn_tunda.setVisibility(GONE);
        }

        loadDataDetail();
        addPaymentViewModel = ViewModelProviders.of(this).get(AddPaymentViewModel.class);
        cancelOrderViewModel = ViewModelProviders.of(this).get(CancelOrderViewModel.class);
        btn_file.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_tunda.setOnClickListener(this);
    }

    private void loadDataDetail() {
        getDetailDataOrderViewModel.getDetailDataOrder(orderData.getOrderId()).observe(this, new Observer<TransactionResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onChanged(TransactionResponse transactionResponse) {
                if (transactionResponse.getStatus()) {
                    if(transactionResponse.getData().size() > 0){
                        readData(transactionResponse.getData());
                        int totalharga = Integer.parseInt(transactionResponse.getTotal_harga());
                        int ongkir = Integer.parseInt(orderData.getShippingCosts());
                        int biayaTambahan = Integer.parseInt(orderData.getAdditionalCosts());
                        int subTotal = totalharga + ongkir + biayaTambahan;
                        tv_totalharga.setText("Rp " +  String.format("%,d",subTotal));
                    }
                }
            }
        });
    }

    private void readData(List<DataTransaction> data) {
        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(this,data);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv_detail.setAdapter(detailOrderAdapter);
        rv_detail.setLayoutManager(lm);
        rv_detail.setNestedScrollingEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataDetail();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            saveImageUpload();
        }
    }

    private void saveImageUpload() {
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();
        String id_order = orderData.getOrderId();
        if(mediaPath == null){
            Toast.makeText(getApplicationContext(),"Pilih Gambar terlebih dahulu ...", Toast.LENGTH_LONG).show();
        }else{
            final File imageFile = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
            final MultipartBody.Part partImage = MultipartBody.Part.createFormData("image",imageFile.getName(),requestBody);
            addPaymentViewModel.addPayment(partImage,RequestBody.create(MediaType.parse("text/plain"),id_order)).observe(this, new Observer<MessageOnly>() {
                @Override
                public void onChanged(MessageOnly messageOnly) {
                    if(messageOnly.getStatus()){
                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }else{
                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @SuppressLint("InflateParams")
    private void displayDialog(){
        builder = new AlertDialog.Builder(this);
        myview = getLayoutInflater().inflate(R.layout.dialog_upload,null,false);
        builder.setView(myview);
        btn_gallery = myview.findViewById(R.id.btn_gallery);
        imageGallery = myview.findViewById(R.id.imageGallery);
        btn_submit_file = myview.findViewById(R.id.btn_submit_file);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,REQUEST_PICK_PHOTO);
            }
        });
        btn_submit_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermision();
            }
        });
        builder.show();

    }

    private void dialogCancel(){
        builder = new AlertDialog.Builder(this);
        myview = getLayoutInflater().inflate(R.layout.container_cancel,null,false);
        builder.setView(myview);
        builder.setTitle("Form Pembatalan Pesanan");
        builder.setCancelable(true);
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelOrder();
            }
        });

        builder.show();
    }

    private void cancelOrder() {
        String id_order = orderData.getOrderId();
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();
        cancelOrderViewModel.cancelOrder(id_order).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == this.RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data != null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    if(cursor != null){
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mediaPath = cursor.getString(columnIndex);
                        imageGallery.setImageURI(data.getData());
                        cursor.close();
                        postPath = mediaPath;
                    }
                }
            }
        }
    }


    private void requestPermision() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUES_WRITE_PERMISION);
        }else{
            saveImageUpload();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_file:
                displayDialog();
                break;

            case R.id.btn_cancel:
                dialogCancel();
                break;

            case R.id.btn_tunda:
                showDialogTunda();
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showDialogTunda() {
        builder = new AlertDialog.Builder(this);
        myview = getLayoutInflater().inflate(R.layout.dialog_tunda,null,false);
        builder.setView(myview);
        builder.setTitle("Form Ubah Jadwal");
        edt_tanggal = myview.findViewById(R.id.edt_tanggal);
        Button btn_resume = myview.findViewById(R.id.btn_resume);
        edt_tanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()){
                    new DatePicker(new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                            int bulan = month+1;
                            edt_tanggal.setText(year + "-" + bulan + "-" + dayOfMonth);
                        }
                    }).show(getSupportFragmentManager(),"Tanggal");
                }
                return true;
            }
        });
//        final String date = edt_tanggal.getText().toString();
        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aturJadwal();
            }
        });
        builder.show();
    }

    private void aturJadwal() {
        String id_shipping = orderData.getNumber();
        String date = edt_tanggal.getText().toString();

        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();

        resumeShippingViewModel.resumeShipping(id_shipping,date).observe(this, new Observer<MessageOnly>() {
            @Override
            public void onChanged(MessageOnly messageOnly) {
                if(messageOnly.getStatus()){
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}