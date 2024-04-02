package com.example.soeapplication;

import android.text.TextUtils;

public class ProductClass {
    String useruid ,name, infomation, cost, imageUrl;

    public ProductClass() {
    }

    public ProductClass(String useruid, String name, String infomation, String cost, String imageUrl) {
        this.useruid = useruid;
        this.name = name;
        this.infomation = infomation;
        this.cost = cost;
        this.imageUrl = imageUrl;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
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
    public String getShortInfomation() {
        String short_infomation = infomation;
        String[] words = short_infomation.split("\\s+"); // Tách chuỗi thành mảng các từ
        if (words.length > 25) {
            // Nếu số từ vượt quá 25, lược bớt các từ và thay thế bằng dấu ...
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 25; i++) {
                sb.append(words[i]).append(" ");
            }
            sb.append("..."); // Thêm dấu ... vào cuối chuỗi
            short_infomation = sb.toString();
        }
        return short_infomation;
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
