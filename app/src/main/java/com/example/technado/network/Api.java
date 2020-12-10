package com.example.technado.network;

import com.example.technado.models.Article;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("articles")
    Call<Article> getAllArticles();

    @GET("articles?")
    Call<Article> getPaginatedArticles(@Query("page") int page);

}
