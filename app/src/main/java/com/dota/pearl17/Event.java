package com.dota.pearl17;

/**
 * Created by ADMIN on 7.3.17.
 */

public class Event {

    private int id;
    private String name;
    private String desc;
    private String club;
    private String rules;
    private String prizes="";
    private String contact="";

    public Event(int id, String name, String desc, String club, String rules, String prizes, String contact) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.rules = rules;
        this.club = club;
        this.prizes = prizes;
        this.contact = contact;
    }

    public Event() {
    }

    public String getPrizes() {
        return prizes;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPrizes(String prize) {
        this.prizes = prize;
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

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
