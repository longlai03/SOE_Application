package com.example.soeapplication.HelperClass;

import java.io.Serializable;

public class UserClass implements Serializable {
    String mUserId, sex, name, email, address, balance;

    public UserClass(String mUserId, String sex, String name, String email, String address, String balance) {
        this.mUserId = mUserId;
        this.sex = sex;
        this.name = name;
        this.email = email;
        this.address = address;
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserClass() {
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
