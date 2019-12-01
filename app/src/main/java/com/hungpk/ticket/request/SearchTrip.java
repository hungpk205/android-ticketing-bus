package com.hungpk.ticket.request;

public class SearchTrip {
    String start_place;
    String end_place;
    String start_time;

    public SearchTrip(String start_place, String end_place, String start_time) {
        this.start_place = start_place;
        this.end_place = end_place;
        this.start_time = start_time;
    }

    public String getStart_place() {
        return start_place;
    }

    public void setStart_place(String start_place) {
        this.start_place = start_place;
    }

    public String getEnd_place() {
        return end_place;
    }

    public void setEnd_place(String end_place) {
        this.end_place = end_place;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
