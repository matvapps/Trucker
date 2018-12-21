package com.foora.perevozkadev.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRemote {

    public static Retrofit retrofit;
    private static int timeoutSeconds = 90;

    public static  <T> T create(Class<T> clazz, String baseUrl) {
        T service = getRetrofit(baseUrl).create(clazz);
        return service;
    }

    public static ApiService getApi() {
        return create(ApiService.class, "http://dev.perevozka.me/api/");
    }


    public static Retrofit getRetrofit(String baseUrl) {

        if (retrofit == null) {
            OkHttpClient.Builder builder =
                    new OkHttpClient.Builder()
                            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
                            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

}
