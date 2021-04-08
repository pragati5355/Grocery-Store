package com.example.grocerystore;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @POST("updateproduct1.php")
    Call<AddProfilePojo> getProfile(@QueryMap Map<String,String>  param);

}
