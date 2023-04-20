package com.example.myapplication.models;

import android.text.format.Time;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Obaveza {
    private int id;
    private String start;
    private String end;

    private LocalTime time;
    private String title;
    private String description;
    private int priority;

    public static int LOW = 1;
    public static int MID = 2;
    public static int HIGH = 3;

    public Obaveza() {

    }

    public Obaveza(int id, String start, String end, String title, String description, int priority) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.time = LocalTime.now();
    }

    public int getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
