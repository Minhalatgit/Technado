package com.example.technado.models;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("current_page")
    int currentPage;

    @SerializedName("from")
    int from;

    @SerializedName("last_page")
    int lastPage;

    @SerializedName("path")
    String path;

    @SerializedName("per_page")
    int perPage;

    @SerializedName("to")
    int to;

    @SerializedName("total")
    int total;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getFrom() {
        return from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public String getPath() {
        return path;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTo() {
        return to;
    }

    public int getTotal() {
        return total;
    }
}
