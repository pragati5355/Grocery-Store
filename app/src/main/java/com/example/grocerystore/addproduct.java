package com.example.grocerystore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addproduct extends  Fragment {

    Button Addbtn;
    EditText productname,productprize,productbrand,productdiscount;
    CircleImageView circleImageView;

    String PostPath="" , filePath="";
    private static String web_url ="https://vaporized-halyards.000webhostapp.com/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addproduct,container,false);

        Addbtn = view.findViewById(R.id.addproduct);
        productname = view.findViewById(R.id.productname);
        productprize = view.findViewById(R.id.productprize);
        productbrand = view.findViewById(R.id.productbrand);
        productdiscount = view.findViewById(R.id.productdiscount);
        circleImageView = view.findViewById(R.id.cicleImageView);

        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveprofile();

            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = CropImage.activity()
                        .setAspectRatio(100,100)
                        .getIntent(getContext());

                startActivityForResult(intent,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode==Activity.RESULT_OK)
            {

                Uri resultUri=result.getUri();

                filePath=getRealPathFromUri(resultUri);
                Glide.with(getContext())
                        .load(filePath)
                        .into(circleImageView);

                PostPath=filePath;
            }
        }
    }

    public String getRealPathFromUri(Uri contentUri)
    {
        String result;

        Cursor cursor = getActivity().getBaseContext().getContentResolver().query(contentUri,null,null,null,null);

        if (cursor == null)
        {
            result = contentUri.getPath();

        }
        else
        {

            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

            result = cursor.getString(index);

            cursor.close();

        }

        return result;
    }

    public void saveprofile()
    {

        Retrofit retrofit = null;

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                          .baseUrl(web_url)
                          .addConverterFactory(GsonConverterFactory.create())
                          .build();
        }

        MultipartBody.Part img = null;

        if (!PostPath.equalsIgnoreCase(""))
        {

            File file = new File(PostPath);

            RequestBody imagedata = RequestBody.create(MediaType.parse("image/*"),file);

            img = MultipartBody.Part.createFormData("profileImage",file.getName(),imagedata);

        }

        RequestBody studentName = RequestBody.create(MediaType.parse("plain/text"),productname.getText().toString());

        RequestBody studentPrice = RequestBody.create(MediaType.parse("plain/text"),productprize.getText().toString());

        RequestBody studentBrand = RequestBody.create(MediaType.parse("plain/text"),productbrand.getText().toString());

        RequestBody studentDiscount = RequestBody.create(MediaType.parse("plain/text"),productdiscount.getText().toString());

        MyApiInterface myApiInterface;
        myApiInterface = retrofit.create(MyApiInterface.class);

        Map<String, RequestBody> param = new HashMap<>();
        param.put("name",studentName);
        param.put("price",studentPrice);
        param.put("brand",studentBrand);
        param.put("discount",studentDiscount);

        Call<AddProfilePojo> call = myApiInterface.addProfile(param,img);

        call.enqueue(new Callback<AddProfilePojo>() {
            @Override
            public void onResponse(Call<AddProfilePojo> call, Response<AddProfilePojo> response) {

                if (response.body().getSucess() == 1)
                {
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
                else if (response.body().getSucess() == 2)
                {
                   Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddProfilePojo> call, Throwable t) {

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
