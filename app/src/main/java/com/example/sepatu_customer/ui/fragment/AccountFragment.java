package com.example.sepatu_customer.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sepatu_customer.R;
import com.example.sepatu_customer.model.MessageOnly;
import com.example.sepatu_customer.network.Constanta;
import com.example.sepatu_customer.session.SystemDataLocal;
import com.example.sepatu_customer.ui.home.HomeActivity;
import com.example.sepatu_customer.ui.login.LoginActivity;
import com.example.sepatu_customer.ui.profile.ChangeAddressActivity;
import com.example.sepatu_customer.ui.profile.ChangeEmailActivity;
import com.example.sepatu_customer.ui.profile.ChangePasswordActivity;
import com.example.sepatu_customer.ui.profile.ChangePictureViewModel;
import com.example.sepatu_customer.ui.profile.ChangeProfileActivity;
import com.example.sepatu_customer.utils.DialogClass;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AccountFragment extends Fragment implements View.OnClickListener {



    SystemDataLocal systemDataLocal;
    TextView tv_nama,tv_email,tv_address;
    ImageView iv_alert,iv_alert_profile,imageGallery;
    TextView tv_logout;
    CardView cardViewProfile,cardViewPassword,cardViewAddress,cardViewEmail;
    ImageView imageProfile;
    Button btn_gallery,btn_submit_file;
    AlertDialog.Builder builder;
    View myview,dialogView;
    private final int REQUEST_PICK_PHOTO = 2;
    private final int REQUES_WRITE_PERMISION = 786;
    private String mediaPath;
    private String postPath;
    private ChangePictureViewModel changePictureViewModel;
    HomeActivity activity = (HomeActivity) getContext();
    private android.app.AlertDialog alertDialog;
    OnCallbackReceived mCallback;
    public interface OnCallbackReceived {
        public void Update();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            saveImageUpload();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        systemDataLocal = new SystemDataLocal(getContext());
        tv_nama = view.findViewById(R.id.tv_nama);
        tv_email = view.findViewById(R.id.tv_email);
        tv_address = view.findViewById(R.id.tv_address);
        tv_nama.setText(systemDataLocal.getLoginData().getFull_name());

        iv_alert = view.findViewById(R.id.iv_alert);
        iv_alert_profile = view.findViewById(R.id.iv_alert_profile);
        tv_logout = view.findViewById(R.id.tv_logout);
        cardViewProfile = view.findViewById(R.id.cardViewProfile);
        imageProfile = view.findViewById(R.id.circleImageView);
        cardViewAddress = view.findViewById(R.id.cardViewLocation);
        cardViewPassword = view.findViewById(R.id.cardViewPassword);
        cardViewEmail = view.findViewById(R.id.cardViewEmail);
        changePictureViewModel = ViewModelProviders.of(this).get(ChangePictureViewModel.class);

        if(systemDataLocal.getLoginData().getAlamat().equals("")){
            tv_address.setText("-");
            iv_alert.setVisibility(View.VISIBLE);
        }else{
            tv_address.setText(systemDataLocal.getLoginData().getAlamat());
            iv_alert.setVisibility(GONE);
        }

        if(systemDataLocal.getLoginData().getNo_hp().equals("")){
            iv_alert_profile.setVisibility(View.VISIBLE);
        }else{
            iv_alert_profile.setVisibility(GONE);
        }

        String email = systemDataLocal.getLoginData().getEmail();
        try {
            tv_email.setText(maskString(email,3,7,'*'));
        } catch (Exception e) {
            e.printStackTrace();
        }


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systemDataLocal.destroySession();
//                mCallback.Update();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });


        cardViewProfile.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
        cardViewPassword.setOnClickListener(this);
        cardViewAddress.setOnClickListener(this);
        cardViewEmail.setOnClickListener(this);

    }

    private static String maskString(String strText, int start, int end, char maskChar)
            throws Exception{

        if(strText == null || strText.equals(""))
            return "";

        if(start < 0)
            start = 0;

        if( end > strText.length() )
            end = strText.length();

        if(start > end)
            throw new Exception("End index cannot be greater than start index");

        int maskLength = end - start;

        if(maskLength == 0)
            return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for(int i = 0; i < maskLength; i++){
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + maskLength);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cardViewProfile:
                startActivity(new Intent(getContext(), ChangeProfileActivity.class));
                break;

            case R.id.circleImageView:
                displayDialog();
                break;

            case  R.id.cardViewPassword:
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
                break;

            case R.id.cardViewLocation:
                startActivity(new Intent(getContext(), ChangeAddressActivity.class));
                break;

            case R.id.cardViewEmail:
                startActivity(new Intent(getContext(), ChangeEmailActivity.class));
                break;
        }
    }

    @SuppressLint("InflateParams")
    private void displayDialog(){
        builder = new AlertDialog.Builder(getContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == activity.RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data != null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
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

    @Override
    public void onResume() {
        super.onResume();
        tv_nama.setText(systemDataLocal.getLoginData().getFull_name());
        if(systemDataLocal.getLoginData().getNo_hp().equals("")){
            iv_alert_profile.setVisibility(VISIBLE);
        }else{
            iv_alert_profile.setVisibility(GONE);
        }
        if(systemDataLocal.getLoginData().getAlamat().equals("")){
            tv_address.setText("-");
            iv_alert.setVisibility(View.VISIBLE);
        }else{
            tv_address.setText(systemDataLocal.getLoginData().getAlamat());
            iv_alert.setVisibility(GONE);
        }
        if(!systemDataLocal.getLoginData().getPhoto().equals("")){
            Glide.with(getContext()).load(Constanta.BASE_URL_IMG_PROFILE + systemDataLocal.getLoginData().getPhoto()).into(imageProfile);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCallbackReceived) context;
        } catch (ClassCastException e) {

        }
    }

    public void saveImageUpload(){
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(getContext(),v).create();
        alertDialog.show();
        String id_users = systemDataLocal.getLoginData().getId_users();
        if(mediaPath == null){
            Toast.makeText(getContext(),"Pilih Gambar terlebih dahulu ...", Toast.LENGTH_LONG).show();
        }else{
            final File imageFile = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
            final MultipartBody.Part partImage = MultipartBody.Part.createFormData("image",imageFile.getName(),requestBody);
            changePictureViewModel.changePicture(partImage,RequestBody.create(MediaType.parse("text/plain"),id_users)).observe(this, new Observer<MessageOnly>() {
                @Override
                public void onChanged(MessageOnly messageOnly) {
                    if(messageOnly.getStatus()){
                        systemDataLocal.editAllSessionLogin(systemDataLocal.getLoginData().getId_users(),
                                systemDataLocal.getLoginData().getUsername(),
                                systemDataLocal.getLoginData().getFull_name(),
                                systemDataLocal.getLoginData().getPassword(),
                                systemDataLocal.getLoginData().getEmail(),
                                systemDataLocal.getLoginData().getRole(),
                                systemDataLocal.getLoginData().getTgl_lahir(),
                                systemDataLocal.getLoginData().getJenis_kelamin(),
                                systemDataLocal.getLoginData().getAlamat(),
                                systemDataLocal.getLoginData().getLatitude(),
                                systemDataLocal.getLoginData().getLongtitude(),
                                imageFile.getName(),
                                systemDataLocal.getLoginData().getNo_hp());
                        Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                        Glide.with(getContext()).load(Constanta.BASE_URL_IMG_PROFILE + systemDataLocal.getLoginData().getPhoto()).into(imageProfile);
                    }else{
                        Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }
                }
            });
        }
    }

    public void requestPermision(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUES_WRITE_PERMISION);
        }else{
            saveImageUpload();
        }
    }
}