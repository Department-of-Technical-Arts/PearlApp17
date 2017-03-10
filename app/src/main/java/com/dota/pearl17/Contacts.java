package com.dota.pearl17;

/**
 * Created by SHREEDA on 11-03-2017.
 */

public class Contacts {
    int image;
    String name,designation,mobile;

    public Contacts(String name, String designation, String mobile,int image) {
        this.name = name;
        this.image=image;
        this.designation = designation;
        this.mobile = mobile;


    }

    public int getImage()
    {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getMobile() {
        return mobile; }




}
