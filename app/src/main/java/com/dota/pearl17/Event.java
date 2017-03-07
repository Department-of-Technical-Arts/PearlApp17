package com.dota.pearl17;

/**
 * Created by ADMIN on 7.3.17.
 */

public class Event {

    private int id;
    private String name;
    private String desc;
    private String contact_name;
    private String contact_mail;
    private String contact_phone;
    private String club;
    private String location;
    private String time;

    public Event(int id, String name, String desc, String contact_name, String contact_mail,
                 String contact_phone, String club, String location, String time) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.contact_name = contact_name;
        this.contact_mail = contact_mail;
        this.contact_phone = contact_phone;
        this.club = club;
        this.location = location;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_mail() {
        return contact_mail;
    }

    public void setContact_mail(String contact_mail) {
        this.contact_mail = contact_mail;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
