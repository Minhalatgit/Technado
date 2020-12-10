package com.example.technado.models;

import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("first")
    String first;

    @SerializedName("last")
    String last;

    @SerializedName("prev")
    String previous;

    @SerializedName("next")
    String next;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }
}
