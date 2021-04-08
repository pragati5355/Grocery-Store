package com.example.grocerystore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {

    Context mcontext;
    ArrayList<AddProfilePojo.ProfileList> profileLists;

    public ProfileAdapter(Context mcontext, ArrayList<AddProfilePojo.ProfileList> profileLists) {
        this.mcontext = mcontext;
        this.profileLists = profileLists;
    }

    @NonNull
    @Override
    public ProfileAdapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.customlist,parent,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileHolder holder, int position) {

          Glide.with(mcontext).load(viewproduct.web_url+profileLists.get(position).getProfileImg())
                  .into(holder.circleImageView);

          holder.name.setText(profileLists.get(position).getName());
          holder.price.setText(profileLists.get(position).getPrice());
          holder.brand.setText(profileLists.get(position).getBrand());
          holder.discount.setText(profileLists.get(position).getDiscount());

    }

    @Override
    public int getItemCount() {
        return profileLists.size();
    }

    public class ProfileHolder extends RecyclerView.ViewHolder {

        TextView name, price, brand, discount;
        CircleImageView circleImageView;

        public ProfileHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.prize);
            brand = itemView.findViewById(R.id.brand);
            discount = itemView.findViewById(R.id.discountno);
            circleImageView = itemView.findViewById(R.id.imagecircleview);


        }
    }


}
