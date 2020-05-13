package com.jkstudio.todonote.model;


import java.io.Serializable;

public class TodoNote implements Serializable {

    private String id, title, detail, date;

    public TodoNote() {}

    public TodoNote(String id, String title, String detail) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = findCurrentDate();
    }

    public TodoNote(String id, String title, String detail, String date) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String findCurrentDate() {
        return "04/05/2020";
    }

}
