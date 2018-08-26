package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.helpers.JSONConvert;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.example.ndiaz.parquesbsas.constants.NetworkUrls.API_URL;

public class RetrofitService {

    private RetrofitApi api;

    public RetrofitService() {
    }

    public RetrofitApi getClient() {

        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(JacksonConverterFactory.create(JSONConvert.getMapper()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            this.api = retrofit.create(RetrofitApi.class);
        }

        return api;
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
