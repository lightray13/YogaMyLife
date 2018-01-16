package com.halfway.yogamylife;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    static final String BASE_URL = "http://api.yogamy.live/";

    private static Context context;

    public Controller (Context context) {
        this.context = context;
    }

    public static RequestInterface getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        return requestInterface;
    }

}
