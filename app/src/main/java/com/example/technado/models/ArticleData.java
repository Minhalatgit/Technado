package com.example.technado.models;

import com.google.gson.annotations.SerializedName;

public class ArticleData {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("body")
    String body;

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("updated_at")
    String updatedAt;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
