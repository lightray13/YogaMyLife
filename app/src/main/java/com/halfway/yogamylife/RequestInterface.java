package com.halfway.yogamylife;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Header;

public interface RequestInterface {

    @GET("/product/{n}")
    Call <Product> getProduct(@Path("n") int n, @Query("lang") String loc);

    @GET("/product/{n}")
    Call <Image> getImage(@Path("n") int n);

    @GET("/product/{n}")
    Call <Info> getInfo(@Path("n") int n, @Query("lang") String loc);

    @GET("/comment/{n}")
    Call <List<Comment>> getComment(@Path("n") int n);

    @GET("/product/{n}")
    Call <Cart> getCart(@Path("n") int n, @Query("lang") String loc);

    @Headers("Content-Type: application/json")
    @POST("/signup")
    Call<Void> registerUser(@Body JsonObject dataToSend);

    @GET("/user")
    Call<User> getUser(@Header("Cookie") String Cookie);

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<User> loginUser(@Body JsonObject dataToSend);

    @Headers("Content-Type: application/json")
    @PUT("/user")
    Call<Void> editUser(@Header("Cookie") String Cookie, @Body User dataToSend);

    @Headers("Content-Type: application/json")
    @PUT("/user")
    Call<Void> editUserJ(@Header("Cookie") String Cookie, @Body JsonObject dataToSend);

    @Headers("Content-Type: application/json")
    @POST("/comment")
    Call<Void> addComment(@Header("Cookie") String Cookie, @Body AddComment dataToSend);

    @Headers("Content-Type: application/json")
    @POST("/purchase")
    Call<Void> addPurchase(@Header("Cookie") String Cookie, @Body AddPurchase dataToSend);

    @GET("/purchase")
    Call <List<Purchase>> getAllPurchase(@Header("Cookie") String Cookie);

    @GET("/logout")
    Call <Void> logoutUser(@Header("Cookie") String Cookie);

}
