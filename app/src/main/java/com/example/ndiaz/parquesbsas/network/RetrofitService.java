package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.BuildConfig;
import com.example.ndiaz.parquesbsas.helpers.JSONConvert;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitService {

    private RetrofitApi api;

    public RetrofitService() {
    }

    public RetrofitApi getClient() {

        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(JacksonConverterFactory.create(JSONConvert.getMapper()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            this.api = retrofit.create(RetrofitApi.class);
        }

        return api;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(getInterceptor());
        builder.addNetworkInterceptor(createLoggingInterceptor());

        return builder.build();
    }

    private Interceptor getInterceptor() {
        return chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (response.code() < 200 || response.code() >= 300) {
                throw new IOException("Error de conexión con el servidor. Intente más tarde.");
            }

            return response;
        };
    }

    private HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor;
        if (BuildConfig.DEBUG) {
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor = new HttpLoggingInterceptor(message -> {
            });
        }
        return loggingInterceptor;
    }
}
