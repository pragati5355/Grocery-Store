package com.example.grocerystore;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class GetDetails extends Fragment {

    Button UpdateBtn, GetBtn;
    EditText Editname,name1,price,brand,discount;
    CircleImageView circleImageView1;

    String filePath="" , PostPath="";

    private static String get_url="https://vaporized-halyards.000webhostapp.com/";
    private static String put_url="https://vaporized-halyards.000webhostapp.com/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.updateproduct,container,false);

        UpdateBtn = view.findViewById(R.id.updateBtn);
        GetBtn = view.findViewById(R.id.getBtn);
        Editname = view.findViewById(R.id.editname);
        name1 = view.findViewById(R.id.getname);
        price = view.findViewById(R.id.getprice);
        brand = view.findViewById(R.id.getbrand);
        discount = view.findViewById(R.id.getdiscount);
        circleImageView1 = view.findViewById(R.id.getimage);

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateData();

            }
        });

        GetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetProfileDetails();

            }
        });

        circleImageView1.setOnClickListener(new View.OnClickListener() {
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

    public void GetProfileDetails()
    {

        Retrofit retrofit=null;
        if(retrofit == null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(get_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        ApiInterface apiInterface;
        apiInterface = retrofit.create(ApiInterface.class);

        Map<String,String> param=new HashMap<>();

        param.put("getname",Editname.getText().toString());

        Call<AddProfilePojo> call = apiInterface.getProfile(param);

        final List<AddProfilePojo.ProfileList> profileLists;
        profileLists=new ArrayList<>();

        call.enqueue(new Callback<AddProfilePojo>() {
            @Override
            public void onResponse(Call<AddProfilePojo> call, Response<AddProfilePojo> response) {

                if (response.body().getSucess() == 1)
                {
                    profileLists.addAll(response.body().getProfileLists());

                    name1.setText(profileLists.get(0).getName());
                    price.setText(profileLists.get(0).getPrice());
                    brand.setText(profileLists.get(0).getBrand());
                    discount.setText(profileLists.get(0).getDiscount());

//                    Log.d("Tag",profileLists.toString());
//                    Log.d("name",profileLists.get(0).getName());

//                    Glide.with(getContext()).load(get_url+ profileLists.get(0).getProfileImg()).into(circleImageView1);


                    Toast.makeText(getContext(),"Data found...",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Data not found...",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AddProfilePojo> call, Throwable t) {


                    Toast.makeText(getContext(),"Data not found...!!"+t,Toast.LENGTH_LONG).show();

            }
        });
    }

    public void UpdateData()
    {
        Retrofit retrofit = null;

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(put_url)
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

        RequestBody studentName = RequestBody.create(MediaType.parse("plain/text"),name1.getText().toString());

        RequestBody studentPrice = RequestBody.create(MediaType.parse("plain/text"),price.getText().toString());

        RequestBody studentBrand = RequestBody.create(MediaType.parse("plain/text"),brand.getText().toString());

        RequestBody studentDiscount = RequestBody.create(MediaType.parse("plain/text"),discount.getText().toString());

        MyApiInterface myApiInterface;
        myApiInterface = retrofit.create(MyApiInterface.class);

        Map<String, RequestBody> param = new HashMap<>();
        param.put("name1",studentName);
        param.put("price1",studentPrice);
        param.put("brand1",studentBrand);
        param.put("discount1",studentDiscount);

        Call<AddProfilePojo> call = myApiInterface.updatedata(param,img);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode== Activity.RESULT_OK)
            {

                Uri resultUri=result.getUri();

                filePath=getRealPathFromUri(resultUri);
                Glide.with(getContext())
                        .load(filePath)
                        .into(circleImageView1);

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
}
