package com.example.laptoptcc.android7_pomodoro.networks.services;

import com.example.laptoptcc.android7_pomodoro.networks.jsonmodels.LoginRespondJson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by laptopTCC on 1/18/2017.
 */

public interface LoginServices {
    @POST("login")
    Call<LoginRespondJson> login(@Body RequestBody body);
}
