package com.example.grocerystore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddProfilePojo {

    @SerializedName("sucess")
    @Expose
    private int sucess;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("profileList")
    @Expose
    private List<ProfileList> profileLists;

    public int getSucess() {
        return sucess;
    }

    public String getMessage() {
        return message;
    }

    public List<ProfileList> getProfileLists() {
        return profileLists;
    }

    public class ProfileList
    {

        @SerializedName("ProductImage")
        @Expose
        private  String profileImg;

        @SerializedName("ProductName")
        @Expose
        String name;

        @SerializedName("ProductPrize")
        @Expose
        private String price;

        @SerializedName("ProductBrand")
        @Expose
        private String brand;

        @SerializedName("ProductDiscount")
        @Expose
        private String discount;

        public String getProfileImg() {
            return profileImg;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getBrand() {
            return brand;
        }

        public String getDiscount() {
            return discount;
        }
    }






}
