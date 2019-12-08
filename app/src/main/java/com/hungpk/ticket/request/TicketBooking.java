package com.hungpk.ticket.request;

import java.io.Serializable;
import java.util.ArrayList;

public class TicketBooking implements Serializable {
    private int trip_id;
    private String fullname;
    private String phone;
    private ArrayList<String> tickets;

    public TicketBooking(int trip_id, String fullname, String phone, ArrayList<String> tickets) {
        this.trip_id = trip_id;
        this.fullname = fullname;
        this.phone = phone;
        this.tickets = tickets;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<String> tickets) {
        this.tickets = tickets;
    }
}
