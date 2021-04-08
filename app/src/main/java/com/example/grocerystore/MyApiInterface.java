package com.example.grocerystore;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface MyApiInterface {


    @Multipart
    @POST("addproduct.php")
    Call<AddProfilePojo> addProfile(@PartMap Map<String, RequestBody> partMap,
                                    @Part MultipartBody.Part file);

    @POST("viewproduct.php")
    Call<AddProfilePojo> getProfile(@QueryMap Map<String,String> param);

    @POST("updateproduct2.php")
    Call<AddProfilePojo> updatedata(@PartMap Map<String,RequestBody> partMap,
                                    @Part MultipartBody.Part file);

}
