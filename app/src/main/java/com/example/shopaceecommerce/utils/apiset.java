package com.example.shopaceecommerce.utils;

import com.example.shopaceecommerce.model.category_response_model;
import com.example.shopaceecommerce.model.login_response_model;
import com.example.shopaceecommerce.model.product_response_model;
import com.example.shopaceecommerce.model.productsbycat_response_model;
import com.example.shopaceecommerce.model.signup_response_model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiset {
    @FormUrlEncoded
    @POST("register.php")
    Call<signup_response_model> getregister(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("gender") String gender,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("dob") String dob
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<login_response_model> getlogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("fetchProducts.php")
    Call<List<product_response_model>> getdata();

    @GET("fetchCategories.php")
    Call<List<category_response_model>> getCategories();

    @POST("fetchProductsByCategory.php")
    Call<List<productsbycat_response_model>> getProductsByCat(
            @Field("catid") int catid
    );
}
