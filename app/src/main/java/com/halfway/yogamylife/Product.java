package com.halfway.yogamylife;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("category")
    @Expose
    private int category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("selectable_parameters")
    @Expose
    private List<SelectableParameter> selectableParameters = null;
    @SerializedName("parameters")
    @Expose
    private List<Parameter> parameters = null;
    @SerializedName("subdescriptions")
    @Expose
    private List<Subdescription> subdescriptions = null;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("count_comments")
    @Expose
    private int countComments;
    @SerializedName("price")
    @Expose
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SelectableParameter> getSelectableParameters() {
        return selectableParameters;
    }

    public void setSelectableParameters(List<SelectableParameter> selectableParameters) {
        this.selectableParameters = selectableParameters;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Subdescription> getSubdescriptions() {
        return subdescriptions;
    }

    public void setSubdescriptions(List<Subdescription> subdescriptions) {
        this.subdescriptions = subdescriptions;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCountComments() {
        return countComments;
    }

    public void setCountComments(int countComments) {
        this.countComments = countComments;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public class Parameter {

        @SerializedName("k")
        @Expose
        private String k;
        @SerializedName("v")
        @Expose
        private String v;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

    }

    public class SelectableParameter {

        @SerializedName("k")
        @Expose
        private String k;
        @SerializedName("v")
        @Expose
        private List<V> v = null;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public List<V> getV() {
            return v;
        }

        public void setV(List<V> v) {
            this.v = v;
        }

    }

    public class Subdescription {

        @SerializedName("k")
        @Expose
        private String k;
        @SerializedName("v")
        @Expose
        private String v;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

    }

    public class V {

        @SerializedName("v")
        @Expose
        private String v;
        @SerializedName("d")
        @Expose
        private int d;

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

    }

}
