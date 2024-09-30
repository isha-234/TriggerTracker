package com.tandt.tracktrigger;

public class Tasks {
    public String task;
    public int[] date;
    public int[] time;
    public String date_d;
    public String time_d;

    public Tasks(String task, int[] date, int[] time){
        this.task = task;
        this.date = date;
        this.time = time;
    }

    public Tasks() {

    }

    public Tasks(String task, String date_d, String time_t) {
        this.task = task;
        this.date_d = date_d;
        this.time_d = time_t;
    }

}