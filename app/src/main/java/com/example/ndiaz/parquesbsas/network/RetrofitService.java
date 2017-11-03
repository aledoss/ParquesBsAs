package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.helpers.JSONConvert;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.example.ndiaz.parquesbsas.constants.Constants.URL;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
    }

    public RetrofitApi getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(JacksonConverterFactory.create(JSONConvert.getMapper()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();

        return retrofit.create(RetrofitApi.class);
    }

    public OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
