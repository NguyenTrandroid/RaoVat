package com.example.raovat.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 8/24/18.
 */

public class APIClient {
    private static APIService ideaService;
    private static String BASE_URL = "https://node-server-api.azurewebsites.net/";
    private static Retrofit INSTANCE = null;
    private static Gson gson;

    public static Retrofit getInstance() {
        if (INSTANCE == null) {

            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Response response = chain.proceed(chain.request());
                                    return response;
                                }
                            })
                    .build();


            INSTANCE = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .build();
        }
        return INSTANCE;
    }


    public static APIService getClient() {
        if (ideaService == null) {
            ideaService = getInstance().create(APIService.class);
        }
        return ideaService;
    }

}