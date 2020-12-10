package com.example.technado.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("data")
    List<ArticleData> articleData;

    @SerializedName("links")
    Link link;

    @SerializedName("meta")
    Meta meta;

    public List<ArticleData> getArticleData() {
        return articleData;
    }

    public Link getLink() {
        return link;
    }

    public Meta getMeta() {
        return meta;
    }
}