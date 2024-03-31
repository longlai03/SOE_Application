package com.example.soeapplication;

public class ProductClass {
    String name, infomation, cost, imageUrl;

    public ProductClass() {
    }

    public ProductClass(String name, String infomation, String cost, String imageUrl) {
        this.name = name;
        this.infomation = infomation;
        this.cost = cost;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfomation() {
        return infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
