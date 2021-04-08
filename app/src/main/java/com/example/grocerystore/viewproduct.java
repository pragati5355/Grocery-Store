package com.example.grocerystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class viewproduct extends Fragment {

    ArrayList<AddProfilePojo.ProfileList> profileLists;
    ProfileAdapter profileAdapter;
    RecyclerView recyclerView;

    public static String web_url="https://vaporized-halyards.000webhostapp.com/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewproduct,container,false);

        recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getProfile();

        return view;
    }

    public void getProfile()
    {

        Retrofit retrofit = null;
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(web_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MyApiInterface myApiInterface;
        myApiInterface = retrofit.create(MyApiInterface.class);

        Map<String,String> param = new HashMap<>();

        Call<AddProfilePojo> call = myApiInterface.getProfile(param);

        call.enqueue(new Callback<AddProfilePojo>() {
            @Override
            public void onResponse(Call<AddProfilePojo> call, Response<AddProfilePojo> response) {

                if (response.body().getSucess() == 1)
                {
                    profileLists = new ArrayList<>();

                    profileLists.addAll(response.body().getProfileLists());
                    profileAdapter = new ProfileAdapter(getContext(),profileLists);

                    recyclerView.setAdapter(profileAdapter);
                    profileAdapter.notifyDataSetChanged();
                }
                else if (response.body().getSucess() == 2)
                {
                    Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddProfilePojo> call, Throwable t) {

                Toast.makeText(getContext(),"Exception"+t,Toast.LENGTH_LONG).show();
            }
        });
    }

}
