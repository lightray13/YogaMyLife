package com.halfway.yogamylife;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddComment {
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("ratings")
    @Expose
    private List<Integer> ratings = null;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }


}
