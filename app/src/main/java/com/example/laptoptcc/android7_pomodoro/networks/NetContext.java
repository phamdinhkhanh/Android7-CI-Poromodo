package com.example.laptoptcc.android7_pomodoro.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laptopTCC on 1/18/2017.
 */

public class NetContext {
 private Retrofit retrofit;
    public NetContext(){
        retrofit = new Retrofit.Builder()
        .baseUrl("https://a-task.herokuapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
}
