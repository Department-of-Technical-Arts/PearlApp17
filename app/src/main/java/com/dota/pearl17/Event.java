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

    public Event(int id, String name, String desc, String club, String rules) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.rules = rules;
        this.club = club;
    }

    public Event() {
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
