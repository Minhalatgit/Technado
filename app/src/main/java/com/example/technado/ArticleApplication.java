package com.example.technado;

import android.app.Application;
import android.content.Context;

import com.example.technado.network.Api;
import com.example.technado.network.RetrofitClient;

public class ArticleApplication extends Application {

    private static ArticleApplication instance;
    private static Api apiCalls;

    public static Context getCtx() {
        return instance.getApplicationContext();
    }

    public static Api getApiCallInstance() {
        return apiCalls;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        apiCalls = RetrofitClient.getRetrofit().create(Api.class);
    }
}
