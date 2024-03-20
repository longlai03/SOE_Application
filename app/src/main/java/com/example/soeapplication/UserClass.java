package com.example.soeapplication;

import com.google.firebase.auth.FirebaseUser;

public class UserClass {
    String mUserId, sex, name, email;

    public UserClass(String mUserId, String sex, String name, String email) {
        this.mUserId = mUserId;
        this.sex = sex;
        this.name = name;
        this.email = email;
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
